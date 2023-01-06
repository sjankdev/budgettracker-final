$('#payment_type').on('change', function() {
    var val = $(this).val();
    $('#Income').hide();
    $('#Expense').hide();
    $('#' + val).show();
});