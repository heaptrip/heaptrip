<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<script src="http://code.jquery.com/jquery-1.9.1.js"></script>
<script src="http://malsup.github.io/jquery.form.js"></script>

<style type="text/css">
    form {
        display: block;
        margin: 20px auto;
        background: #eee;
        border-radius: 10px;
        padding: 15px
    }

    .progress {
        position: relative;
        width: 400px;
        border: 1px solid #ddd;
        padding: 1px;
        border-radius: 3px;
    }

    .bar {
        background-color: #B4F5B4;
        width: 0%;
        height: 20px;
        border-radius: 3px;
    }

    .percent {
        position: absolute;
        display: inline-block;
        top: 3px;
        left: 48%;
    }
</style>

<script type="text/javascript">
    $(function () {
        var bar = $('.bar');
        var percent = $('.percent');
        var status = $('#status');
        $('form').ajaxForm({
            beforeSend: function () {
                status.empty();
                var percentVal = '0%';
                bar.width(percentVal)
                percent.html(percentVal);
            },
            uploadProgress: function (event, position, total, percentComplete) {
                var percentVal = percentComplete + '%';
                bar.width(percentVal)
                percent.html(percentVal);
            },
            success: function (result) {
                var percentVal = '100%';
                bar.width(percentVal)
                percent.html(percentVal);
                $('#image').html('');
                $("#image").append("<img id='upload-image' src=' http://localhost:8080/heaptrip/rest/image?imageId=" + result.fileId + "'/><br/>");
            },
            complete: function (xhr) {
                status.html(xhr.responseText);
            }
        });
    });
</script>

<html>
<head>
    <title>Hello</title>
</head>
<body>
<form action="http://localhost:8080/heaptrip/image/upload" method="POST" enctype="multipart/form-data">
    <input type="file" name="myfile"><br>
    <input type="submit" value="Upload File to Server">
</form>

<div class="progress">
    <div class="bar"></div>
    <div class="percent">0%</div>
    <div id="image"/>
</div>

<div id="status"></div>
</body>
</html>