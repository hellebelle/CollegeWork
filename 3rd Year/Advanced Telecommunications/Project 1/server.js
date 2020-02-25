var https = require('https'),
	http = require('http'),
    NodeCache = require( "node-cache" ),
    SimpleHashTable = require('simple-hashtable'),
    WebSocket = require('ws'),
	URL = require('url'),
	fs = require('fs');

var stdin = process.openStdin();

// Create cache, never delete files unless updated by request
const myCache = new NodeCache({ stdTTL: 3600, checkperiod: 3600 });
const blacklist = new SimpleHashTable();

// Constants for timing
const NS_PER_SEC = 1e9
const MS_PER_NS = 1e6

//Get configurations from "config.json"
var config = JSON.parse(fs.readFileSync("config.json"));
var host = config.host,
	port = config.port;

// Block Youtube to start with
blacklist.put('www.youtube.com', 'blocked');

// Console input listener, block URLs here
stdin.addListener("data", function(data) {

    // Extract command
    var input = data.toString();
	var command = input.substring(0, input.indexOf(' '));
    switch (command){
		case "help": 		help(); break;
		case "block":		block(data); break;
		case "unblock":		unblock(data); break;
		case "quit": 		quit(); break;
		case "status": 		status(); break;
		case "port": 		changeport(data); break;
		case "host": 		changehost(data); break;
		default: 			console.log("\x1b[32m","Unknown command - " + command); break;
	}
});
/*-----------------------HELP----------------------*/
function help(){
	console.log("\x1b[32m","\n\thelp: displays proxy commands\n\n\tblock: blocks URL \n\n\tunblock: unblocks URL \n\n\tquit: terminates the proxy\n\n\tport <port number>: updates the port number of the proxy\n\n\thost <host address>: updates the host address of the proxy\n");
}

/*-------------------CHANGEPORT--------------------*/
//Changes the port of the proxy
function changeport(data){
	var c = JSON.parse(fs.readFileSync("config.json"));
  
	c.port = data.toString().substring(5).trim();
  
	fs.writeFile('config.json', JSON.stringify(c), (err) => {
	  if (err) throw err;
	  console.log("\x1b[31m",'The port number was updated to:'+ data);
	});
  }
  
  /*-------------------CHANGEHOST-------------------*/
  //Changes the host address of the proxy
  function changehost(data){
  
	var c = JSON.parse(fs.readFileSync("config.json"));
  
	c.host = data.toString().substring(5).trim();
  
	fs.writeFile('config.json', JSON.stringify(c), (err) => {
	  if (err) throw err;
	  console.log("\x1b[31m",'The host address was updated to:'+ data);
	});
  }
  

/*---------------------STATUS----------------------*/
function status(){
	console.log("\x1b[32m","host:\t\t"+host+"\nport:\t\t"+port);
}
/*---------------------------------------------WATCH CONFIG------------------------------------------------*/

//Dynamically change the host, port, and blacklist if they are changed in the config file
fs.watchFile("config.json",function(){
	updateConfig();
});

function updateConfig(){
  	config = JSON.parse(fs.readFileSync("config.json"));
  	host = config.host;
  	port = config.port;
  	server.close();
  	server.listen(port,host);
}

// Handle the dynamic blocking of URLs
function block(data){
	var url = data.toString().substring(6).trim();
	//allow to quit
	if(url == "quit"){
		quit();
	}
    blacklist.put(url);
    console.log("\x1b[31m","Successfully blocked URL: " + url);
}

function unblock(data){
	var url = data.toString().substring(8).trim();
	//allow to quit
	if(url == "quit"){
		quit();
	}
    if(blacklist.containsKey(url)){
		blacklist.remove(url);
		console.log("\x1b[31m","Successfully unblocked URL: " + url)
    } else {
          console.log("URL " + url + " not found in blocked URLs");
        }
}
/*-----------------------QUIT----------------------*/
function quit(){
	console.log("quit detected")
	server.close(); abort();
}

// Handle responses
function handleResponse(options, res, c_res, eTimes){
  // Extract URL from options
  var url = options.hostname;
  // Check if URL is blocked
  if(blacklist.containsKey(url)){
    console.log("URL " + url + " is blocked.");
    c_res.write("URL " + url + " is blocked.");
    c_res.end();
    return;
  }
  // Extract status code and expires from response
  const { statusCode } = res;
  const resExpiry = res.headers['expires'];
  let error;
  //If no 200 status received, error
  if (statusCode !== 200) {
    error = new Error('Request Failed.\n' +
    `Status Code: ${statusCode}`);
  }
  // Handle response error
  if (error) {
    console.error(error.message);
    c_res.write(error.message);
    c_res.end();
    return;
  }
  var cacheHit = false;
  // Start cache lookup time
  eTimes.cacheLookupAt = process.hrtime()
  // Check cache for web page and verify expires
  cacheRes = myCache.get(url);
      if(cacheRes == undefined){
        console.log("\x1b[31m","URL " + url + " not found in cache. Continuing with request...");
        console.log("######################################################################");
      }else{
        console.log("\x1b[31m","URL found in cache, verifying cache page hasn't expired...");

        var cachedExpiryDate = Date.parse(cacheRes.expiry);
        var responseExpiryDate = Date.parse(resExpiry);

        // If cache expiry equal or better than response expiry cache hit
        if (cachedExpiryDate >= responseExpiryDate){
          console.log("\x1b[31m","Cached page has not expired - returning...")
          c_res.write(cacheRes.body);
          c_res.end();
          eTimes.cacheReturnAt = process.hrtime();

          var responseSizeB = Buffer.byteLength(cacheRes.body, 'utf8');
          var responseSizeKB = responseSizeB/1024;

          // Calculate and display total response size
          console.log("######################################################################");
          console.log("\x1b[31m","Cached response size:  " + cacheRes.body.length + " characters, " + responseSizeB + " bytes", responseSizeKB + " KB");
          console.log("######################################################################");

          eTimes.endTime = process.hrtime()
          var cacheLookupTime = convertTimes(eTimes.cacheLookupAt, eTimes.cacheReturnAt);

          // Display timings
          console.log("Process                          | Time Taken (ms)                       ");
          console.log("_____________________________________________________________________");
          console.log("DNS Lookup                       | 0");
          console.log("TCP Connection                   | 0");
          console.log("TLS Handshake                    | 0");
          console.log("Cache Lookup                     | " + cacheLookupTime);
          console.log("_____________________________________________________________________");
          console.log("Total Request Time               | " + cacheLookupTime + " ms");

          // Calculate bandwidth (KB/s)
          var bandwidth = (responseSizeKB/(cacheLookupTime*0.001)).toFixed(6)
          console.log("Total Request Bandwidth          | " + bandwidth + " KB/s");

          cacheHit = true;
        } else{
          console.log("Cached response expired - fetching up to date response...");
          console.log("######################################################################");
        }
       }

  // If url not found in cache, continue with request and cache new response
  if(!cacheHit){
    res.setEncoding('utf8');

    let rawData = '';

    // When data is received
    res.on('data', (chunk) => {
      rawData += chunk;
    });

    // When response is finished
    res.on('end', () => {

      eTimes.endTime = process.hrtime()

      var responseSizeB = Buffer.byteLength(rawData, 'utf8');
      var responseSizeKB = responseSizeB/1024;

      // Calculate and display total response size
      console.log("#######################################################################");
      console.log("Total response size:  " + rawData.length + " characters, " + responseSizeB + " bytes", responseSizeKB + " KB");
      console.log("#######################################################################");

      var timings = getTimings(eTimes);

      // Display timings
      console.log("#Process                          | Time Required (ms)                #");
      console.log("#_____________________________________________________________________#");
      console.log("#DNS Lookup                       | " + timings.dnsLookup );
      console.log("#TCP Connection                   | " + timings.tcpConnection );
      console.log("#TLS Handshake                    | " + timings.tlsHandshake);
      console.log("#_____________________________________________________________________#");
      console.log("#Total Request Time               | " + timings.total + " ms");

      // Calculate bandwidth (KB/s)
      var bandwidth = (responseSizeKB/(timings.total*0.001)).toFixed(6)
	  console.log("#Total Request Bandwidth          | " + bandwidth + " KB/s");
	  console.log("#######################################################################");


      // Create cache object with expiry
      cacheObject = {
        expiry: resExpiry,
        body: rawData
      }

      myCache.set(url, cacheObject, (err, success) => {
        if(!err && success){
          console.log("\x1b[31m","\nSuccessfully added " + url + " to cache");
        } else{
          console.log("\x1b[31m","\nFailed to add " + url + " to cache");
        }
      })

      c_res.write(rawData);
      c_res.end();
    });
  }
}

// Handle requests
//c_req = Client Request c_res = Client Response
function onRequest(c_req, c_res) {
    // Record specific event times here
    const eTimes = {
      // use process.hrtime() to record time
      srtTime: process.hrtime(),
      dnsLookupAt: undefined,
      tcpConnectionAt: undefined,
      tlsHandshakeAt: undefined, 
      endTime: undefined,
      cacheLookupAt: undefined,
      cacheReturnAt: undefined
    }
    var options = URL.parse(c_req.url.substring(1), true);
    // Only handle HTTP and HTTPS requests
    if(options.protocol == 'http:' || options.protocol == 'https:'){
      // Filter out requests
      if(options.path != 'favicon.ico' && options.hostname != 'assets'){
        console.log("\x1b[31m",'\nReceived request for: ' + options.protocol + '//'+ options.hostname);
        var proxy_req = null;
        // Handle http and https request seperately
        switch(options.protocol){
          case 'http:':
            proxy_req = http.get(options.href, (res) => handleResponse(options, res, c_res, eTimes));
            break;
          case 'https:':
            proxy_req = https.get(options.href, (res) => handleResponse(options, res, c_res, eTimes))
            .on('socket', (socket) => {
              // DNS Lookup Time
              socket.on('lookup', () => {
                eTimes.dnsLookupAt = process.hrtime();
              })
              // TCP connection Time
              socket.on('connect', () => {
                eTimes.tcpConnectionAt = process.hrtime();
              })
              // If HTTPS record TLS handshake timing
              socket.on('secureConnect', () => {
                eTimes.tlsHandshakeAt = process.hrtime();
              })

            });
            break;
          default:
            c_res.write('Invalid request, please enter a valid request such as:\n\nhttp://localhost:[PORT]/https://www.tcd.ie');
            c_res.end();
            break;
		}
		// Handle request timeouts
		proxy_req.on('timeout', () => {
		console.log('\e[32mProxy request timed out...');
		c_res.write('Proxy request timed out...');
		c_res.end();
		proxy_req.abort();
		})

		// Handle request errors
		proxy_req.on('error', (e) => {
		console.error(`Got error: ${e.message}`);
		c_res.write(`Got error: ${e.message}`);
		c_res.end();
		proxy_req.abort();
		});
      } else{
        c_res.end();
      }
    } else{
      c_res.write('Invalid request, please enter a valid request such as:\n\nhttp://localhost:4000/https://www.tcd.ie');
      c_res.end();
    }
}

// Handle WebSocket requests
function handleWebSocketRequest(url, ws){

  // Record specific event times here
  const eTimes = {
    // use process.hrtime() as it's not a subject of clock drift
    srtTime: process.hrtime(),
    dnsLookupAt: undefined,
    tcpConnectionAt: undefined,
    tlsHandshakeAt: undefined,
    endTime: undefined,
    cacheLookupAt: undefined,
    cacheReturnAt: undefined
  }

  var options = URL.parse(url, true);

  // Only handle HTTP and HTTPS requests
  if(options.protocol == 'http:' || options.protocol == 'https:'){

    // Filter out  requests
    if(options.path != 'favicon.ico' && options.hostname != 'assets'){
      console.log('\e[32m\nReceived request for: ' + options.protocol + '//'+ options.hostname);

      var proxy_req = null;

      // Handle http and https request seperately
      switch(options.protocol){
        case 'http:':
          proxy_req = http.get(options.href, (res) => handleWebSocketResponse(options, res, ws, eTimes));
          break;
        case 'https:':
          proxy_req = https.get(options.href, (res) => handleWebSocketResponse(options, res, ws, eTimes))
          .on('socket', (socket) => {
            // DNS Lookup Time
            socket.on('lookup', () => {
              eTimes.dnsLookupAt = process.hrtime();
            })
            // TCP Connection Time
            socket.on('connect', () => {
              eTimes.tcpConnectionAt = process.hrtime();
            })
            // If HTTPS record TLS handshake timing
            socket.on('secureConnect', () => {
              eTimes.tlsHandshakeAt = process.hrtime();
            })
          });
          break;
        default:
          ws.send('Invalid request, please enter a valid request such as:\n\nhttp://localhost:4000/https://www.tcd.ie');
          break;
	  }
	  
	  // Handle request timeouts
	  proxy_req.on('timeout', () => {
	    console.log("\x1b[31m",'Proxy request timed out...');
		ws.send('Proxy request timed out...');
		proxy_req.abort();
	  })

	  // Handle request errors
	  proxy_req.on('error', (e) => {
		console.error(`Got error: ${e.message}`);
		ws.send(`Got error: ${e.message}`);
		proxy_req.abort();
	  });
    }
  } else{
    ws.send('Invalid request, please enter a valid request such as:\n\nhttp://localhost:4000/https://www.tcd.ie');
  }
}

// Handle WebSocket responses
function handleWebSocketResponse(options, res, ws, eTimes){

  // Extract URL from options
  var url = options.hostname;

  // Check if URL is blocked
  if(blacklist.containsKey(url)){
    console.log("URL " + url + " is blocked.");
    ws.send("URL " + url + " is blocked.");
    return;
  }

  // Extract status code and expires from response
  const { statusCode } = res;
  const resExpiry = res.headers['expires'];

  let error;

  //If no 200 status received, error
  if (statusCode !== 200) {
    error = new Error('Request Failed.\n' +
    `Status Code: ${statusCode}`);
  }

  // Handle response error
  if (error) {
    console.error(error.message);
    ws.send(error.message);
    return;
  }

  var cacheHit = false;

  // Check cache for web page and verify expires
  cacheRes = myCache.get(url);

      if(cacheRes == undefined){
        console.log("\x1b[31m","URL " + url + " not found in cache. Continuing with request...");
      }else{
        console.log("\x1b[31m","mURL found in cache, verifying cache page hasn't expired...");

        console.log("\x1b[31m","Cache object expiry: " + cacheRes.expiry);
        console.log("\x1b[31m","Response expiry: " + resExpiry);

        var cachedExpiryDate = Date.parse(cacheRes.expiry);
        var responseExpiryDate = Date.parse(resExpiry);

        // If cache expiry equal or better than response expiry cache hit
        if (cachedExpiryDate >= responseExpiryDate){
          console.time('Cached Request Time');
          console.log("\x1b[31m","Cached page has not expired - returning...")
          ws.send(cacheRes.body);
          console.timeEnd('Cached Request Time');
          cacheHit = true;
        } else{
          console.log("\x1b[31m","Cached response expired - fetching up to date response...");
        }
      }

  // If url not found in cache, continue with request and cache new response
  if(!cacheHit){
    res.setEncoding('utf8');

    let rawData = '';
    console.time('Non-Cached Request Time');

    // When data is received
    res.on('data', (chunk) => { rawData += chunk; });

    // When response is finished
    res.on('end', () => {

      console.timeEnd('Non-Cached Request Time');
      eTimes.endTime = process.hrtime()
      var timings = getTimings(eTimes);
      console.log(timings);

      // Create cache object with expiry
      cacheObject = {
        expiry: resExpiry,
        body: rawData
      }

      myCache.set(url, cacheObject, (err, success) => {
        if(!err && success){
          console.log("\x1b[31m","Successfully added " + url + " to cache");
        } else{
          console.log("\x1b[31m","Failed to add " + url + " to cache");
        }
      })

      ws.send(rawData);
    });
  }
}

// Calculates all of the stored timing values
function getTimings (eTimes) {
  return {
    // There is no DNS lookup with IP address
    dnsLookup: eTimes.dnsLookupAt !== undefined ? convertTimes(eTimes.srtTime, eTimes.dnsLookupAt) : undefined,
    tcpConnection: convertTimes(eTimes.dnsLookupAt || eTimes.srtTime, eTimes.tcpConnectionAt),
    tlsHandshake: eTimes.tlsHandshakeAt !== undefined ? (convertTimes(eTimes.tcpConnectionAt, eTimes.tlsHandshakeAt)) : undefined,
    cachedResponseTime: (eTimes.cacheLookupAt != undefined && eTimes.cacheReturnAt != undefined) ? convertTimes(eTimes.cacheLookupAt, eTimes.cacheReturnAt) : undefined,
    total: convertTimes(eTimes.srtTime, eTimes.endTime)
  }
}

// Converts times to ms
function convertTimes (startTime, endTime) {
  const secondDiff = endTime[0] - startTime[0]
  const nanoSecondDiff = endTime[1] - startTime[1]
  const diffInNanoSecond = secondDiff * NS_PER_SEC + nanoSecondDiff

  return diffInNanoSecond / MS_PER_NS
}

// WebServer
var server = http.createServer(onRequest).listen(port, function () {
  console.log('Now listening on port 9000! Go to http://localhost:9000/')
})

// WebSocket server
var wsServer = new WebSocket.Server({ server });

// Handle connections to WebSocket server
wsServer.on('connection', function connection(ws) {

  console.log("\x1b[31m","Received websocket connection...");

  ws.on('message', function incoming(message) {
    console.log("\x1b[31m",'Received WebSocket request for: %s', message);
    handleWebSocketRequest(message, ws);
  });
});
