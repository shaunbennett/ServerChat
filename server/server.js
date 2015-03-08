var express = require('express'),
  app = express(),
  redis = require("redis"),
  client = redis.createClient(6379, "localhost", {}),
  clientpub = redis.createClient(6379, "localhost", {}),
  channels = require('./model/channel.js');

var server = server = app.listen(3000, function() {
  console.log("Web server started!");
});

var io = require('socket.io').listen(server);

// Log Redis Errors
client.on("error", function(err) {
	console.log("Error " + err);
});

// Subscribe to all pubsub channels
client.psubscribe("serverchat.*");

app.set('view engine', 'ejs');
app.get('/', function(req, res) {
  res.render('index', {
    'ip': req.connection.remoteAddress
  });
});

// socket.io connection from web
io.on('connection', function(socket) {
  // Connection Message
  clientpub.publish(channels.ClientStatusUpdate,
    socket.handshake.address + " has connected to web client.")

  client.on('pmessage', function(pattern, channel, message) {
    var msgObject = {
      message: message,
      channel: channel
    };

    socket.emit('chatUpdate', msgObject);
  });

  socket.on('chatMessage', function(msg) {
    clientpub.publish(channels.ClientMessage,
      socket.handshake.address + " > " + msg);
  });

  socket.on('disconnect', function() {
    clientpub.publish(channels.ClientStatusUpdate,
      socket.handshake.address + " has disconnected from web client.")
  });
});

// use public directory for static files
app.use(express.static(__dirname + '/public'));
