const WebSocket = require('ws');
var validUrl = require('valid-url');
var stdin = process.openStdin();

// Console input listener, block URLs here
stdin.addListener("data", function(data) {

    // Retrieve command
    var input = data.toString();
    var command = input.substring(0, input.indexOf(' '));

    switch(command){
      //request handling
      case "request":
        var url = data.toString().substring(8).trim();

        if(validUrl.isUri(url)){
          console.log("Valid URI - Sending request to proxy...");
          ws.send(url);
        } else {
          console.log("Invalid URL - " + url + "\n");
        }
        break;

      default:
        console.log("Unknown command - " + command);
        break;
    }
});

const ws = new WebSocket('ws://localhost:9000');

ws.on('open', function open() {
  console.log("Successful WebSocket connection to proxy via ws://localhost:9000");
});

ws.on('message', function incoming(message) {
    console.log('Received response from proxy: %s', message);
});

ws.on('close', function closed() {
  console.log("WebSocket connection to proxy was closed");
})