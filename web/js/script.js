function setCookie(cname, cvalue, exdays) {
    var d = new Date();
    d.setTime(d.getTime() + (exdays * 24 * 60 * 60 * 1000));
    var expires = "expires=" + d.toUTCString();
    document.cookie = cname + "=" + cvalue + "; " + expires;
}

function getCookie(cname) {
    var name = cname + "=";
    var ca = document.cookie.split(';');
    for (var i = 0; i < ca.length; i++) {
        var c = ca[i];
        while (c.charAt(0) == ' ') c = c.substring(1);
        if (c.indexOf(name) == 0) return c.substring(name.length, c.length);
    }
    return "";
}

function encodeOrder(object) {
    var strArr = [];
    for (var index in object) {
        var value = object[index];
        strArr.push(index + "=" + value);
    }
    return strArr.join('-');
}

function decodeOrder(str) {
    var strArr = str.split('-');
    var object = {};
    strArr.forEach(function(item) {
        var objVal = item.split('=');
        object[objVal[0]] = objVal[1];
    });
    return object;
}

$(".js-order").click(function () {
    var cookieName = 'order';
    var id = $(this).closest('.item').data('id');
    var order = getCookie(cookieName);

    var menuOrder = {};
    if (order != "") {
        menuOrder = decodeOrder(order);
    }

    if (typeof menuOrder["id" + id] == 'undefined') {
        menuOrder["id" + id] = 1;
    } else {
        menuOrder["id" + id]++;
    }
    setCookie(cookieName, encodeOrder(menuOrder), 30);
    alert('Ok!');
});