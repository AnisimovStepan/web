// Shorthand for $( document ).ready()
$(function() {
    $('#userEditModel').on('show.bs.modal', function (event) {
        // Root Tr
        var row = $(event.relatedTarget).parent().parent();
        var modal = $(this);

        row.children().each(function(index, element ) {
            switch (index) {
                case 1:
                    modal.find('#loginModal').val(element.textContent);
                    break;
                case 2:
                    modal.find('#adminModal').prop("checked", element.textContent.indexOf('ADMIN') > 0);
                    modal.find('#userModal').prop("checked", element.textContent.indexOf('USER') > 0);
                    break;
                case 3:
                    modal.find('#firstNameModal').val(element.textContent);
                    break;
                case 4:
                    modal.find('#lastNameModal').val(element.textContent);
                    break;
                case 5:
                    modal.find('#middleNameModal').val(element.textContent);
                    break;
            }
        });
    });

    $('#deleteModal').click(function (event) {
        var modal = $('#userEditModel');
        // Root Tr
        var login = modal.find('#loginModal').val();
        $.ajax({
            url: '/admin/delete-user',
            type: "POST",
            data: {login: login},
            success: function () {
                document.location.reload();
            }
        });
    });

    $('#saveModel').click(function (event) {
        var modal = $('#userEditModel');
        var data = {
            login: modal.find('#loginModal').val(),
            password: 'dummy',
            firstName: modal.find('#firstNameModal').val(),
            lastName: modal.find('#lastNameModal').val(),
            middleName: modal.find('#middleNameModal').val()
        };

        if (modal.find('#adminModal').prop('checked')) {
            data['ADMIN'] = 'on';
        }
        if (modal.find('#userModal').prop('checked')) {
            data['USER'] = 'on';
        }

        // Root Tr
        var login = modal.find('#loginModal').val();
        $.ajax({
            url: '/admin/edit-user',
            type: "POST",
            data: data,
            success: function () {
                document.location.reload();
            }
        });
    });
});
