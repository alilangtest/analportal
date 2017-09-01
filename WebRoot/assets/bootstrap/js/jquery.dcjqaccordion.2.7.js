(function ($) {



    $.fn.dcAccordion = function (options) {
        var defaults = {
            classParent: 'dcjq-parent',
            classActive: 'active',
            classArrow: 'dcjq-icon',
            classCount: 'dcjq-count',
            classExpand: 'dcjq-current-parent',
            eventType: 'click',
            hoverDelay: 300,
            menuClose: true,
            autoClose: true,
            autoExpand: false,
            speed: 'slow',
            saveState: true,
            disableLink: true,
            showCount: false,
        };
        var options = $.extend(defaults, options);
        this.each(function (options) {
            var obj = this;
            setUpAccordion();
            if (defaults.autoExpand == true) {
                $('li.' + defaults.classExpand + ' > a').addClass(defaults.classActive);
            }
            resetAccordion();
            if (defaults.eventType == 'hover') {
                var config = {
                    sensitivity: 2,
                    interval: defaults.hoverDelay,
                    over: linkOver,
                    timeout: defaults.hoverDelay,
                    out: linkOut
                };
                $('li a', obj).hoverIntent(config);
                var configMenu = {
                    sensitivity: 2,
                    interval: 1000,
                    over: menuOver,
                    timeout: 1000,
                    out: menuOut
                };
                $(obj).hoverIntent(configMenu);
                if (defaults.disableLink == true) {
                    $('li a', obj).click(function (e) {
                        if ($(this).siblings('ul').length > 0) {
                            e.preventDefault();
                        }
                    });
                }
            } else {
                $('li a', obj).click(function (e) {
                    $activeLi = $(this).parent('li');
                    $parentsLi = $activeLi.parents('li');
                    $parentsUl = $activeLi.parents('ul');
                    if (defaults.disableLink == true) {
                        if ($(this).siblings('ul').length > 0) {
                            e.preventDefault();
                        }
                    }
                    if (defaults.autoClose == true) {
                        autoCloseAccordion($parentsLi, $parentsUl);
                    }
                    if ($('> ul', $activeLi).is(':visible')) {
                        $('ul', $activeLi).slideUp(defaults.speed);
                        $('a', $activeLi).removeClass(defaults.classActive);
                    } else {
                        $(this).siblings('ul').slideToggle(defaults.speed);
                        $('> a', $activeLi).addClass(defaults.classActive);
                    }
                });
            }

            function setUpAccordion() {
                $arrow = '<span class="' + defaults.classArrow + '"></span>';
                var classParentLi = defaults.classParent + '-li';
                $('> ul', obj).show();
                $('li', obj).each(function () {
                    if ($('> ul', this).length > 0) {
                        $(this).addClass(classParentLi);
                        $('> a', this).addClass(defaults.classParent).append($arrow);
                    }
                });
                $('> ul', obj).hide();
                if (defaults.showCount == true) {
                    $('li.' + classParentLi, obj).each(function () {
                        if (defaults.disableLink == true) {
                            var getCount = parseInt($('ul a:not(.' + defaults.classParent + ')', this).length);
                        } else {
                            var getCount = parseInt($('ul a', this).length);
                        }
                        $('> a', this).append(' <span class="' + defaults.classCount + '">' + getCount + '</span>');
                    });
                }
            }

            function linkOver() {
                $activeLi = $(this).parent('li');
                $parentsLi = $activeLi.parents('li');
                $parentsUl = $activeLi.parents('ul');
                if (defaults.autoClose == true) {
                    autoCloseAccordion($parentsLi, $parentsUl);
                }
                if ($('> ul', $activeLi).is(':visible')) {
                    $('ul', $activeLi).slideUp(defaults.speed);
                    $('a', $activeLi).removeClass(defaults.classActive);
                } else {
                    $(this).siblings('ul').slideToggle(defaults.speed);
                    $('> a', $activeLi).addClass(defaults.classActive);
                }
                if (defaults.saveState == true) {
                    createCookie(defaults.cookie, obj);
                }
            }

            function linkOut() {}

            function menuOver() {}

            function menuOut() {
                if (defaults.menuClose == true) {
                    $('ul', obj).slideUp(defaults.speed);
                    $('a', obj).removeClass(defaults.classActive);
                    createCookie(defaults.cookie, obj);
                }
            }

            function autoCloseAccordion($parentsLi, $parentsUl) {
                $('ul', obj).not($parentsUl).slideUp(defaults.speed);
                $('a', obj).removeClass(defaults.classActive);
                $('> a', $parentsLi).addClass(defaults.classActive);
            }

            function resetAccordion() {
                $('ul', obj).hide();
                $('a.' + defaults.classActive, obj).siblings('ul').show();
            }
        });
    };
})(jQuery);