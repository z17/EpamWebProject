var cookieName = 'order';

$(".js-order, .js-order-item-more").click(function () {
    var $orderCount = $('.js-order-count');
    var countOrder = $orderCount.data('count');
    if (typeof countOrder == 'undefined') {
        countOrder = 0;
    }
    countOrder++;
    $orderCount.data('count', countOrder);
    $orderCount.text("(" + countOrder + ")");

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
    var $item = $(this).closest('.item');
    var currentItemCount = $item.find('.js-order-item-count').text();
    currentItemCount++;
    $item.find('.js-order-item-count').text(currentItemCount);

    setCookie(cookieName, encodeOrder(menuOrder), 30);
    alert('Ok!');
});


$(".js-order-item-less").click(function () {
    var $orderCount = $('.js-order-count');
    var countOrder = $orderCount.data('count');
    if (typeof countOrder == 'undefined') {
        count = 0;
    }
    countOrder--;
    if (countOrder < 0) {
        countOrder = 0;
    }

    $orderCount.data('count', countOrder);
    $orderCount.text("(" + countOrder + ")");

    var id = $(this).closest('.item').data('id');
    var order = getCookie(cookieName);

    var menuOrder = {};
    if (order != "") {
        menuOrder = decodeOrder(order);
    }

    if (!typeof menuOrder["id" + id] == 'undefined') {
        menuOrder["id" + id]--;
        if (menuOrder["id" + id] < 0) {
            menuOrder["id" + id] = 0;
        }
    }

    var $item = $(this).closest('.item');
    var currentItemCount = $item.find('.js-order-item-count').text();
    currentItemCount--;
    if (currentItemCount < 0) {
        currentItemCount = 0
    }
    $item.find('.js-order-item-count').text(currentItemCount);

    setCookie(cookieName, encodeOrder(menuOrder), 30);
    alert('Ok!');
});


$(document).ready(function () {
    var order = getCookie(cookieName);
    var menuOrder = {};
    if (order != "") {
        menuOrder = decodeOrder(order);
    }
    var sumItems = 0;
    for (var index in menuOrder) {
        var id = index.substr(2);
        $('.item[data-id=' + id + '] .order-item-count').text(menuOrder[index]);
        sumItems += parseInt(menuOrder[index]);
    }
    var $orderCount = $('.js-order-count');
    $orderCount.text("(" + sumItems + ")");
    $orderCount.data('count', sumItems);
});


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
    strArr.forEach(function (item) {
        var objVal = item.split('=');
        object[objVal[0]] = objVal[1];
    });
    return object;
}