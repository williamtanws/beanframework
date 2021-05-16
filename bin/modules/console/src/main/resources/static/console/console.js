$('.content-wrapper').IFrame({
    onTabClick(item) {
        return item
    },
    onTabChanged(item) {
        return item
    },
    onTabCreated(item) {
        return item
    },
    loadingScreen: 750,
    autoIframeMode: true,
    autoItemActive: true,
    autoShowNewTab: true,
    loadingScreen: true,
    useNavbarItems: true,
    scrollOffset: 40,
    scrollBehaviorSwap: false,
    iconMaximize: 'fa-expand',
    iconMinimize: 'fa-compress',
    allowDuplicates: false
});

function changeLocale(locale) {
    var url = window.location.href.split('#')[0];
    url = String(url.replace(
        /&?locale=([^&]$|[^&]*)/i, ""));
    if (window.location.href.indexOf('?') > -1) {
        url = url + '&locale=' + locale;
    } else {
        url = url + '?locale=' + locale;
    }

    window.location.href = url;
}

$(document).ajaxError(function(event, jqxhr, settings, exception) {
    if (jqxhr.status == 401) {
        window.top.location.href = loginUrl;
    }
});