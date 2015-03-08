function formatTime(date) {
  var hours = date.getHours();
  var minutes = date.getMinutes();
  var ampm = hours >= 12 ? 'pm' : 'am';
  hours = hours % 12;
  hours = hours ? hours : 12;
  minutes = minutes < 10 ? '0' + minutes : minutes;
  return hours + ':' + minutes + ' ' + ampm;
}

function getListClass(channel) {
  var liclass;

  switch(channel) {
    case "serverchat.errormessage":
      liclass = "list-group-item list-group-item-danger";
      break;
    case "serverchat.playerstatusupdate":
      liclass = "list-group-item list-group-item-success";
      break;
    case "serverchat.clientstatusupdate":
      liclass = "list-group-item list-group-item-info";
      break;
    default:
      liclass = "list-group-item";
  }

  return liclass;
};

var socket = io();

$('#msgform').submit(function() {
  socket.emit('chatMessage', $('#m').val());
  $('#m').val('');
  return false;
});

socket.on('chatUpdate', function(msg) {
  var formattedTime = formatTime(new Date());
  var liclass = getListClass(msg.channel);

  var inner = $('<li class="' + liclass + '">')
    .text(msg.message)
    .append('<span class="badge">' + formattedTime + '</span>');

  $('#messages').append(inner);
});
