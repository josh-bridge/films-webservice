<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <script src="webjars/jquery/3.2.1/jquery.min.js"></script>
    <title>Film Server</title>
    <style type="text/css">
        body {
            margin: 0;
            padding: 0;
            background: rgb(181, 229, 236);
            background: linear-gradient(45deg, rgb(163, 225, 236) 0%, rgb(199, 242, 220) 100%);
            font-family: Arial, sans-serif;
        }

        #title {
            margin-top: 20px;
            text-align: center;
            color: #000
        }

        #filmTable {
            font-size: 14px
        }

        #main {
            background-color: #FFFFFF;
            width: 1000px;
            margin: 0 auto;
            padding: 20px;
            border-radius: 4px;
            box-shadow: 1px 1px 5px rgba(0, 0, 0, 0.05);
            margin-bottom: 20px;
        }

        #allFilmsTitle {
            display: inline;
        }

        #allFilmsTitleContainer {
            margin: 10px 0;
        }

        table {
            border-collapse: collapse;
        }

        table, th, td {
            border: 1px solid rgb(221, 221, 221);;
        }

        td {
            width: 150px;
            padding: 0;
            margin: 0;
        }

        #filmTable td {
            padding: 7px;
        }

        #createFilmTable input, #createFilmTable textarea {
            padding: 7px;
            height: 20px;
        }

        th {
            padding: 10px;
        }

        .bullet {
            color: #888888;
        }

        .filmInput {
            width: 136px;
            border: 0;
            outline: 0;
        }

        .filmTable {
            width: 100%;
        }

        textarea {
            resize: none;
        }

        .control {
            text-align: center;
        }

        #searchBar {
            float: right;
            padding: 5px;
        }

        #searchForm {
            display: inline;
        }

        #searchSubmit {
            float: right;
            padding: 5px;
        }

        #editFilmForm {
            display: none;
        }
    </style>
    <script type="text/javascript">

        function refreshTableWithFilms(films) {
            const filmTable = $("#filmTable");
            var rows = [];

            // clear table - but not the headers
            filmTable.find("tr:not(:first-child)").each(function(key, row) {
                $(row).remove()
            });

            $.each(films, function(key, film) {
                rows.push("<tr filmId='" + film.id + "'>" +
                    "<td>" + film.title + "</td>" +
                    "<td>" + film.year + "</td>" +
                    "<td>" + film.director + "</td>" +
                    "<td>" + film.stars + "</td>" +
                    "<td>" + getReview(film.review) + "</td>" +
                    "<td class='control'>" +
                    "<a href='" + film.links.self.href + "'>View</a> " +
                    "<a href='#' class='editFilmLink' filmId='" + film.id + "' filmlink='" + film.links.self.href + "'>Edit</a> " +
                    "<a href='#' class='deleteFilmLink' filmlink='" + film.links.self.href + "'>Delete</a>" +
                    "</td></tr>");
            });

            filmTable.append(rows);
        }

        function refreshAllFilms() {
            $.getJSON("films", function(data) {
                refreshTableWithFilms(data["results"]);
            });
        }

        function searchForFilms(url, method, term) {
            $.ajax({
                url: url,
                type: method,
                data: {
                    title: term
                },
                dataType: 'json',
                success: function(data) {
                    refreshTableWithFilms(data["results"]);
                }
            });
        }

        function deleteFilm(url) {
            $.ajax({
                url: url,
                type : "DELETE",
                success : function() {
                    refreshAllFilms();
                }
            });
        }

        function setEditFilmFormValues(film) {
            if (film == null) {
                alert("Not a valid film!");
                return;
            }

            var editFilmForm = $("#editFilmForm");

            editFilmForm.attr("action", "films/"+film.id);
            $(editFilmForm.find(":input[name='title']")[0]).val(film.title);
            $(editFilmForm.find(":input[name='year']")[0]).val(film.year);
            $(editFilmForm.find(":input[name='director']")[0]).val(film.director);
            $(editFilmForm.find(":input[name='stars']")[0]).val(film.stars);
            $(editFilmForm.find("textarea[name='review']")[0]).val(film.review);

            editFilmForm.css("display", "block");
            $("#createFilmForm").css("display", "none");
        }

        function populateEditFilmForm(filmId) {
            $.getJSON("films/" + filmId, function(data) {
                setEditFilmFormValues(data);
            });
        }

        function sendFormAsJson(form, method, onSuccess) {
            $.ajax({
                url: form.attr("action"),
                type : method,
                contentType: "application/json; charset=utf-8",
                data : JSON.stringify(objectifyForm(form.serializeArray())),
                dataType : 'json',
                statusCode: {
                    200: onSuccess
                },
                error: function(error) {
                    console.log(error)
                }
            });
        }

        function clearForm(form) {
            var clearAll = function(key, item) {
                $(item).val("");
            };

            form.find(":input[type='text']").each(clearAll);
            form.find("textarea").each(clearAll);
        }

        function setupFilmForm() {
            var createForm = $("#createFilmForm");
            createForm.submit(function(ev) {
                var onSuccess = function () {
                    refreshAllFilms();
                    clearForm(createForm);
                };

                sendFormAsJson(createForm, createForm.attr("method"), onSuccess);
                ev.preventDefault();
            });
        }

        function clearEditForm() {
            var editFilmForm = $("#editFilmForm");
            clearForm(editFilmForm);
            editFilmForm.css("display", "none");
            $("#createFilmForm").css("display", "block")
        }

        function setupEditFilmForm() {
            var editFilmForm = $("#editFilmForm");
            editFilmForm.submit(function(ev) {
                var onSuccess = function () {
                    refreshAllFilms();

                    clearEditForm();
                };

                sendFormAsJson(editFilmForm, "put", onSuccess);
                ev.preventDefault();
            });
        }

        function setupSearchForm() {
            var searchForm = $("#searchForm");
            searchForm.submit(function(ev) {
                searchForFilms(
                    searchForm.attr("action"),
                    searchForm.attr("method"),
                    searchForm.children(":input[type='text']").val()
                );

                ev.preventDefault();
            });
        }

        // Function from - https://stackoverflow.com/a/1186309
        function objectifyForm(formArray) {
            var returnArray = {};
            for (var i = 0; i < formArray.length; i++){
                returnArray[formArray[i]['name']] = formArray[i]['value'];
            }
            return returnArray;
        }

        function getReview(review) {
            if (review == null) {
                return "";
            }

            if (review.length <= 103) {
                return review;
            }

            return review.substring(0,100) + "...";
        }
    </script>
</head>
<body>
<h1 id="title">rest-films</h1>
<div id="main">
    <div id="allFilms">
        <div id="allFilmsTitleContainer">
            <h2 id="allFilmsTitle">All Films</h2>
            <span class="bullet">•</span>
            <a id="refreshAllFilmsButton" href="#" onclick="refreshAllFilms()">Refresh</a>
            <form action="films/search" method="get" id="searchForm">
                <input type="submit" name="searchSubmit" id="searchSubmit" value="Search"/>
                <input type="text" name="title" placeholder="Title" id="searchBar"/>
            </form>
        </div>
        <table id="filmTable" class="filmTable">
            <tr>
                <th>Title</th>
                <th>Year</th>
                <th>Director</th>
                <th>Stars</th>
                <th>Review</th>
                <th>Control</th>
            </tr>
        </table>

        <form id="createFilmForm" action="films" method="post">
            <table id="createFilmTable" class="filmTable">
                <tr>
                    <td><input id='titleInput' name='title' class='filmInput' type='text' placeholder='Title'/></td>
                    <td><input id='yearInput' name='year' class='filmInput' type='text' placeholder='Year'/></td>
                    <td><input id='directorInput' name='director' class='filmInput' type='text' placeholder='Director'/></td>
                    <td><input id='starsInput' name='stars' class='filmInput' type='text' placeholder='Stars'/></td>
                    <td><textarea id='reviewInput' name='review' class='filmInput' placeholder='Review'></textarea></td>
                    <td><input type='submit' value="Add film"/></td>
                </tr>
            </table>
        </form>

        <form id="editFilmForm" action="films" method="post">
            <%--<input type="hidden" name="id" value="">--%>
            <table id="editFilmTable" class="filmTable">
                <tr>
                    <td><input id='editTitleInput' name='title' class='filmInput' type='text' placeholder='Title'/></td>
                    <td><input id='editYearInput' name='year' class='filmInput' type='text' placeholder='Year'/></td>
                    <td><input id='editDirectorInput' name='director' class='filmInput' type='text' placeholder='Director'/></td>
                    <td><input id='editStarsInput' name='stars' class='filmInput' type='text' placeholder='Stars'/></td>
                    <td><textarea id='editReviewInput' name='review' class='filmInput' placeholder='Review'></textarea></td>
                    <td>
                        <input type='submit' value="Submit Edit"/>
                        <a id="cancelEditForm" href="#">Cancel</a>
                    </td>
                </tr>
            </table>
        </form>

    </div>
</div>

<script type="text/javascript">
    $(document).ready(function() {
        refreshAllFilms()
    });

    var body = $("body");
    body.on("click", ".deleteFilmLink", function() {
        deleteFilm($(this).attr("filmLink"));
    });

    body.on("click", ".editFilmLink", function() {
        populateEditFilmForm($(this).attr("filmId"));
    });

    body.on("click", "#cancelEditForm", function() {
        clearEditForm();
    });

    $(function () {
        setupFilmForm();

        setupEditFilmForm();

        setupSearchForm();
    });
</script>
</body>
</html>
