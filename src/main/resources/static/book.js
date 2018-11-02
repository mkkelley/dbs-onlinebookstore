window.onload = function () {
    $('.addCartButton').click(function () {
        var btn = $(this);
        btn.removeClass('btn-info');
        btn.addClass('btn-warning');
        btn.html('Adding...');
        let token = $("meta[name='_csrf']").attr("content");
        let header = $("meta[name='_csrf_header']").attr("content");
        let headers = {};
        headers[header] = token;
        $.ajax({
            type: 'POST',
            url: '/cart/add',
            data: {'isbn': $(this).attr('data')},
            headers: headers,
            success: function () {
                btn.html("Added");
                btn.removeClass('btn-warning');
                btn.addClass('btn-success');
            },
            failure: function () {
                btn.html("Failed to add to cart.");
                btn.removeClass('btn-warning');
                btn.addClass('btn-danger');
            }
        })
    })
};
