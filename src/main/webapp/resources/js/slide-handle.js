function sendR(obj){
    radius = $(obj).val();
    setPlaneScale($(obj).val());
}

function sendX(obj){
    let xval = document.getElementById("x-coordinate");
    alert($(obj).val());
    xval.setAttribute("value", $(obj).val());
}

function handleClickLink(x){
    document.getElementsByTagName('input')[2].value = x;
    document.getElementById('output-x').value = x;
}