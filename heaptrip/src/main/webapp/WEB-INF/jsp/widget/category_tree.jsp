<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>

<script type="text/javascript">

    var getSelectedCategories = function () {
        var checked_ids = [];
        $("#category .tree").jstree("get_checked", null, true).each(function () {
            if (this.parentElement.parentElement.className.indexOf('jstree-checked') == -1)
                checked_ids.push(this.id);
        });
        return checked_ids;
    };

    var selectCategories = function (categoryIdArr) {
        window.block = true;
        $('#category .tree').jstree("uncheck_all");
        $.each(categoryIdArr, function (index, val) {
            $('#category .tree').jstree("check_node", "#" + val.replace(/\./g, "\\."));
        });
        var checked_ids = getSelectedCategories();
        if (checked_ids.length > 0) {
            $.putLOCALParamToURL({ct: checked_ids.join()});
        }

        if ($('#sideRight').attr('filter') == 'read_only') {
            $("#category .tree").jstree("get_unchecked", null, true).each(function (a, e) {
                var id = $(e).attr("id").replace(/\./g, "\\.");
                $("#category .tree").jstree("delete_node", "#" + id);
            });
            $("#category .tree").jstree("get_checked", null, true).each(function () {
                $.jstree._reference('#category .tree').set_type("disabled", "#" + this.id.replace(/\./g, "\\."));

            });
        }
        window.block = false;

    };

    var unSelectCategories = function (categoryIdArr) {
        window.block = true;
        $('#category .tree').jstree("uncheck_all");
        window.block = false;
    };

    $(window).bind("onPageReady", function (e, paramsJson) {
        if (paramsJson.ct) {
            selectCategories(paramsJson.ct.split(','));
        } else {
            unSelectCategories();
        }
    });

    $.delayLoading('getInitCategoryIds');

    $(document).ready(function () {

        if ($('#sideRight').attr('filter') == 'read_only') {
            $("#category_zag").text(locale.wgt.categories);
        }else
            $("#category_zag").text(locale.wgt.categorySelect);

        var url = '../rest/categories';

        var callbackSuccess = function (data) {

            $('#category .tree').jstree({
                json_data: {
                    data: data.categories
                },
                "plugins": [ "themes", "json_data", "checkbox", 'types' ],
                "types": {
                    "types": {
                        "disabled": {
                            "check_node": false,
                            "uncheck_node": false
                        }
                    }
                }

            }).bind("loaded.jstree", function () {
                        var paramsJson = $.getParamFromURL();
                        if (paramsJson.ct) {
                            selectCategories(paramsJson.ct.split(','));
                        } else if (data.userCategories) {
                            selectCategories(data.userCategories);
                        }
                        $('.tree').css("height", "auto")

                    })
                    .bind("change_state.jstree", function (node, uncheck) {
                        if (!window.block) {
                            if (window.timeout)
                                clearTimeout(window.timeout);
                            window.timeout = setTimeout(function () {
                                $.handParamToURL({ct: getSelectedCategories().join()});
                            }, 1000);
                        }
                    })
            $.allowLoading('getInitCategoryIds', {ct: data.userCategories ? data.userCategories.join() : $.getParamFromURL().ct});
        };

        var callbackError = function (error) {
            $.alert(error);
        };

        var guid = null;

        if ($.getParamFromURL().ct == undefined) {
            if ($('#sideRight').attr('filter') != 'empty') {
                if (window.catcher)
                    guid = window.catcher.id;
                else if (window.principal) {
                    guid = window.principal.id;
                }
            }
        }

        $.postJSON(url, guid, callbackSuccess, callbackError);
    });


</script>

<div id="category" class="filtr">
    <div id="category_zag" class="zag">""</div>
    <div class="content" style="display: block;">
        <div class="tree" style="height:205px;"></div>
        <tiles:insertAttribute name="category_tree_btn"/>
    </div>
</div>