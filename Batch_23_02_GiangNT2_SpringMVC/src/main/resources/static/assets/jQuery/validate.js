function checkAnpal(params) {
    let PT_ANPAL = /^[a-zA-Z ]*$/;
    return !PT_ANPAL.test(params);
}
function checkAnpalIsSpace(params) {
    let PT_ANPAL = /^[a-zA-Z]*$/;
    return !PT_ANPAL.test(params);
}


function kytudacbiet(params) {
    let PT_KTDB = /^[!@#$%^&*()_+\-=\[\]{};':"\\|,.<>\/?]*$/
    return !PT_KTDB.test(params);
}

function checkLength(params) {
    return !params.length > 0;
}

function checkPhone(params) {
    let PT_PHONE = /^[0-9]{10}$/
    return !PT_PHONE.test(params);
}

function checkZipcode(params) {
    let PT_PHONE = /^[0-9]{5}$/
    return !PT_PHONE.test(params);
}

function checkCVV(params) {
    let PT_PHONE = /^[0-9]{3}$/
    return !PT_PHONE.test(params);
}

function displayNone(no, booalen, feedback) {
    booalen ? no.css("display", "none") : no.css("display", "block").css("color", "red").text(feedback).css("font-family", "'Gill Sans', 'Gill Sans MT', Calibri, 'Trebuchet MS', sans-serif");
}

function checkNumber(number) {
    let PT_ANPAL = /^\d*$/;
    return !PT_ANPAL.test(number);
}


function checkEmail(string) {
    let PT_EMAIL = /^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/;
    return !PT_EMAIL.test(string);
}


function validURL(str) {
    var pattern = new RegExp('^(https?:\\/\\/)?' + // protocol
        '((([a-z\\d]([a-z\\d-]*[a-z\\d])*)\\.)+[a-z]{2,}|' + // domain name
        '((\\d{1,3}\\.){3}\\d{1,3}))' + // OR ip (v4) address
        '(\\:\\d+)?(\\/[-a-z\\d%_.~+]*)*' + // port and path
        '(\\?[;&a-z\\d%_.~+=-]*)?' + // query string
        '(\\#[-a-z\\d_]*)?$', 'i'); // fragment locator

    return pattern.test(str);
}

function checkBirthDayTypeText(params) {
   var birthday = new  Date(params)
   var currusdate = new Date();
   return birthday.getTime() - currusdate.getTime() >0 ;
}



function checkundefined(params) {
    return params == undefined;
}

function checkSothuc(params) {
    let PT_SOTHUC = /^([0-9]{1}.[0-9]{1,2}|[0-9]{1,2}|)$/;
    return !PT_SOTHUC.test(params);
}

function checkDiaChi(params) {
    let PT_SOTHUC = /^[a-zA-Z0-9\, ]*$/;
    return !PT_SOTHUC.test(params);
}
