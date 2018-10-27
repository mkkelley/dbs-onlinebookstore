window.onload = function () {
    $('.addCartButton').click(function () {
        var btn = $(this);
        btn.removeClass('btn-info');
        btn.addClass('btn-warning');
        btn.html('Adding...');
        $.ajax({
            type: 'POST',
            url: '/cart/add',
            data: {'isbn': $(this).attr('data')},
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
