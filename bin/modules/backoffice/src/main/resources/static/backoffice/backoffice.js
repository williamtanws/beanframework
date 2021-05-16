
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

function menuOnClick(path, target) {
    if (target != "_self") {
        window.open(path, target).focus();
    }
}

function openUserSession() {
    $('.content-wrapper').IFrame('createTab',userSessionTitlee,userSessionLinkk, '-backoffice-user-session', true);
}

function changeUserSettings(data){
	$.ajax({
        url: userSettingsUrl,
        data: data,
        success: function(result) {},
        error: function(data) {
            console.log(data);
        }
    });
}

$('#user-settings-header-theme').change(function() {
    if ($(this).is(':checked')) {
		$('.main-header').removeClass('navbar-white');
		$('.main-header').addClass('navbar-dark');
    	changeUserSettings({
            userSettingsHeaderTheme: "navbar-dark"
        });
    } else {
		$('.main-header').removeClass('navbar-dark');
		$('.main-header').addClass('navbar-white');
    	changeUserSettings({
            userSettingsHeaderTheme: "navbar-white"
        });
    }
});

$('#user-settings-sidebar-theme').change(function() {
    if ($(this).is(':checked')) {
		$('.main-sidebar').removeClass('sidebar-dark-primary');
		$('.main-sidebar').addClass('sidebar-light-primary');
    	changeUserSettings({
            userSettingsSidebarTheme: "sidebar-light-primary"
        });
    } else {
		$('.main-sidebar').removeClass('sidebar-light-primary');
		$('.main-sidebar').addClass('sidebar-dark-primary');
    	changeUserSettings({
            userSettingsSidebarTheme: "sidebar-dark-primary"
        });
    }
});

$('#user-settings-sidebar-navflatstyle').change(function() {
    if ($(this).is(':checked')) {
		$('.nav-sidebar').addClass('nav-flat');
    	changeUserSettings({
            userSettingsSidebarNavflatstyle: "nav-flat"
        });
    } else {
		$('.nav-sidebar').removeClass('nav-flat');
    	changeUserSettings({
        	userSettingsSidebarNavflatstyle: ""
        });
    }
});

$('#user-settings-sidebar-navlegacystyle').change(function() {
    if ($(this).is(':checked')) {
		$('.nav-sidebar').addClass('nav-legacy');
    	changeUserSettings({
            userSettingsSidebarNavlegacystyle: "nav-legacy"
        });
    } else {
		$('.nav-sidebar').removeClass('nav-legacy');
    	changeUserSettings({
        	userSettingsSidebarNavlegacystyle: ""
        });
    }
});

$('#user-settings-sidebar-navcompact').change(function() {
    if ($(this).is(':checked')) {
		$('.nav-sidebar').addClass('nav-compact');
    	changeUserSettings({
            userSettingsSidebarNavcompact: "nav-compact"
        });
    } else {
		$('.nav-sidebar').removeClass('nav-compact');
    	changeUserSettings({
        	userSettingsSidebarNavcompact: ""
        });
    }
});

$('#user-settings-sidebar-navchildindent').change(function() {
    if ($(this).is(':checked')) {
		$('.nav-sidebar').addClass('nav-child-indent');
    	changeUserSettings({
            userSettingsSidebarNavchildindent: "nav-child-indent"
        });
    } else {
		$('.nav-sidebar').removeClass('nav-child-indent');
    	changeUserSettings({
        	userSettingsSidebarNavchildindent: ""
        });
    }
});

$('#user-settings-body-theme').change(function() {
    if ($(this).is(':checked')) {
		$('body').addClass('dark-mode');
		$('.iframe-mode .tab-loading').addClass('dark-mode');
		$('.iframe-mode .navbar').removeClass('navbar-white');
		$('.iframe-mode .navbar').addClass('dark-mode');
		$('.iframe-mode .nav-link-theme').removeClass('bg-light');
		$('.iframe-mode .nav-link-theme').addClass('bg-dark');
    	changeUserSettings({
            userSettingsBodyTheme: "dark-mode"
        });
    } else {
		$('body').removeClass('dark-mode');
		$('.iframe-mode .tab-loading').removeClass('dark-mode');
		$('.iframe-mode .navbar').removeClass('dark-mode');
		$('.iframe-mode .navbar').addClass('navbar-white');
		$('.iframe-mode .nav-link-theme').removeClass('bg-dark');
		$('.iframe-mode .nav-link-theme').addClass('bg-light');
    	changeUserSettings({
            userSettingsBodyTheme: ""
        });
    }
    $('.content-wrapper').IFrame('removeActiveTab', 'all');
});

$('#user-settings-body-smalltext').change(function() {
    if ($(this).is(':checked')) {
        $('body').addClass('text-sm');
    	changeUserSettings({
        	userSettingsBodySmalltext: "text-sm"
        });
    } else {
        $('body').removeClass('text-sm');
    	changeUserSettings({
        	userSettingsBodySmalltext: ""
        });
    }
    $('.content-wrapper').IFrame('removeActiveTab', 'all');
});

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
    autoIframeMode: true,
    autoItemActive: true,
    autoShowNewTab: true,
    loadingScreen: 750,
    useNavbarItems: true
});;

$('.dropdown-header').on('click', function(e) {
    e.stopPropagation();
});

$('#notification-messages').on('click', function(e) {
    e.stopPropagation();
})

$(document).ready(function() {
    const checkForNotifications = (delay) => {
        setTimeout(() => {
            $.ajax({
                url: notificationUrl,
                async: true,
                success: function(result) {
                    if (result.total > 0) {
                        $("#notification-total").html(result.total);
                    } else {
                        $("#notification-total").html('');
                    }

                    $("#notification-overall").html(result.overallMessage);

                    if (jQuery.isEmptyObject(result.notificationMessages)) {
                        $("#notification-messages").html('');
                    } else {
                        $.each(result.notificationMessages, function(i, notification) {
                            $("#notification-messages").html("<div class='dropdown-divider'></div><a class='dropdown-item'><i class='" + notification.icon + "'></i>&nbsp;&nbsp;" + notification.message + "<span class='float-right text-muted text-sm'>" + notification.timeAgo + "</span></a>")
                        });
                    }
                },
                error: function(data) {
                    console.log(data);
                }
            }).done(() => {
                // do your staff
                checkForNotifications(notificationInterval)
            })
        }, delay)
    }
    checkForNotifications(0);

    $("#notification-checked").click(function() {
        $.ajax({
            url: notificationCheckedUrl,
            async: true,
            success: function(result) {},
            error: function(data) {
                console.log(data);
            }
        });
    });
});;

$(document).ajaxError(function(event, jqxhr, settings, exception) {
    if (jqxhr.status == 401) {
        window.top.location.href = loginUrl;
    }
});