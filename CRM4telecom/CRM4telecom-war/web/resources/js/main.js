$(document).keydown(function(event) {
    if (event.which == "17")
        cntrlIsPressed = true;
});
$(document).keyup(function() {
    cntrlIsPressed = false;
});
var cntrlIsPressed = false;
function clk(link, e) {
    if (e != undefined)
        event = e;
    if ((event != undefined && event.which === 2) || cntrlIsPressed) {
        if (link == "this")
            window.open(document.URL, '_blank');
        else
            window.open(link, '_blank');
    } else {
        if (link == "this")
            location.reload();
        else
            window.open(link, '_self');
    }
    return false;
}