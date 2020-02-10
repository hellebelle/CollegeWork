var http = require('http'), 
    httpProxy = require('http-proxy');
//Create a proxy instance
var proxy = httpProxy.createProxy();
proxy.on ("error", function(e){
    //error handling
});

function onRequest(request, response){
    //Send the HTTP header
    //HTTP Status: 200 : OK
    //Content Type: text/plain
    response.writeHead(200, {'Content-Type': 'text/plain'});
    response.write('Hello World');
    response.end();
}

http.createServer(onRequest).listen(8000);
console.log('Server running at http://127.0.0.1:8000/');