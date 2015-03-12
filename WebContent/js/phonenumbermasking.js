/**Phone number masking javascript
 * 
 */

$(document).ready(function() {

            //Set mask for phone number field
            $("#txtPhoneNumber").mask("(999) 999-9999");
 
            //Store numbers in hidden field
            $("#txtPhoneNumber").blur(function () {
 
                //Clear the hidden field
                $("#phone").val("");
 
                //Create char array from phone number field
                var charArray = $(this).val().split("");
 
                var phoneNumber = "";
 
                //Iterate over each character in the char array
                //and determine if it is a number
                $.each(charArray, function(index, value) {
                    if (!isNaN(value) && value != " ") {
                        phoneNumber = phoneNumber + value;
                    }
                });
 
                //Set hidden field
                $("#phone").val(phoneNumber);
            });
 
           /** $("#save").click(function () {
                //If valid, show hidden field contents so we can
                //confirm that it is indeed just saving the number
                if($("#employeeForm").valid()) {   
                    alert("Value in hidden field: " + $("#phone").val());
                }
            });*/
        });
 