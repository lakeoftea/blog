var renderTable = function(posts) {

    $("#postTable").empty()
    $("#postTable").append("<table class='table table-dark'>" +
                     "<thead>" +
                     "<tr>" +
                     "<th>Id</th>" +
                     "<th>Title</th>" +
//                     "<th>Tags</th>" +
                     "<th>Summary</th>" +
                     "<th>Link</th>" +
                     "<th>Enabled</th>" +
                     "<th>Content</th>" +
                     "<th>Actions</th>" +
                     "</tr>" +
                     "</thead>" +
                     "<tbody id='tagTable'>" +
                     "</tbody>" +
                     "</table>")

    posts.forEach(function(post) {
        var tagNames = ""
        if(post.tags != null) {
            post.tags.forEach(function(value, index, array) {
                tagNames += value['id']
                if(index != array.length-1) {
                    tagNames += ", "
                }
            })
        }
        if(tagNames == "") {
            tagNames = "No tags"
        }

        $("#tagTable").append("<tr>" +
                              "<th>" + post.id + "</th>" +
                              "<td>" + post.title + "</td>" +
//                              "<td>" + tagNames + "</td>" +
                              "<td>" + post.summary + "</td>" +
                              "<td>" + post.link + "</td>" +
                              "<td>" + post.enabled + "</td>" +
                              "<td>" + post.content + "</td>" +
                              "<td>" +
                              "<svg height='32' class='octicon octicon-x' viewBox='0 0 12 16' version='1.1' width='24' aria-hidden='true'><path id='" + post.id + "' class='deleteButton' fill-rule='evenodd' d='M7.48 8l3.75 3.75-1.48 1.48L6 9.48l-3.75 3.75-1.48-1.48L4.52 8 .77 4.25l1.48-1.48L6 6.52l3.75-3.75 1.48 1.48z'></path></svg>" +
                              "<svg height='32' class='octicon octicon-pencil' viewBox='0 0 14 16' version='1.1' width='28' aria-hidden='true'><path id='" + post.id + "' class='editButton' fill-rule='evenodd' d='M0 12v3h3l8-8-3-3-8 8zm3 2H1v-2h1v1h1v1zm10.3-9.3L12 6 9 3l1.3-1.3a.996.996 0 0 1 1.41 0l1.59 1.59c.39.39.39 1.02 0 1.41z'></path></svg>" +
                              "<svg height='32' class='octicon octicon-diff-added' viewBox='0 0 14 16' version='1.1' width='28' aria-hidden='true'><path id='" + post.id + "' class='copyButton' fill-rule='evenodd' d='M13 1H1c-.55 0-1 .45-1 1v12c0 .55.45 1 1 1h12c.55 0 1-.45 1-1V2c0-.55-.45-1-1-1zm0 13H1V2h12v12zM6 9H3V7h3V4h2v3h3v2H8v3H6V9z'></path></svg>" +
                              "</td>" +
                              "</tr>")
    })
}

var getPosts = function() {
    $.ajax({
        url: '/admin/posts/getposts',
        method: 'get'
    }).done(function(res) {
        if(res.success) {
            renderTable(res.data.posts)
        } else {
            $("#postTable").empty()
        }
    })
}

$(document).ready(function() {

    $("#submit").bind("click", function(e) {

        var data = {
            title: $("#title").val(),
            link: $("#link").val(),
            summary: $("#summary").val(),
            content: $("#content").val(),
            enabled: $("#enabled").prop("checked"),
            tags: $("#tags").val()
        }

        var id = $("#id").val()
        if(id)
            data['id'] = id

        $.ajax({
            url: '/admin/posts/submit',
            data: data,
            method: 'post',
            dataType: 'json',
        }).done(function(res) {
            $("#postForm")[0].reset()
            getPosts()
            renderTags()
        })
    })

    $("#confirmDeleteButton").bind("click", function(event) {
        var id = $("#postName").text()
        $.ajax({
            url: '/admin/posts/deletepost',
            method: 'post',
            data: {id: id}
        }).done(function(res) {
            getPosts()
        })
    })

    getPosts()
    renderTags()
})

$(document).on('click', '.deleteButton', function(event) {

    $("#postName").text(event.target.id)
    $("#confirmDeleteModal").modal({
        show: true
    })

})

var renderTags = function(selected) {
    $("#tags").empty()

    if(selected) {
        var selectedArray = []
        selected.forEach(function(value) {
            selectedArray.push(value.id)
        })
    }


        var matched = false
        $.ajax({
            url: "/admin/tags/getall",
            method: "get"
        }).done(function(res) {
            res.data.tags.forEach(function(value) {
                if (selected) {
                    if (selectedArray.includes(value.id)) {
                        $("#tags").append("<option selected value=" + value.id + ">" + value.name + "</option>")
                        matched = true
                    }
                }
                if(!matched) {
                    $("#tags").append("<option value=" + value.id + ">" + value.name + "</option>")
                }
                matched = false
            })
        })
}

$(document).on('click', '.editButton', function(event) {
    $("#postForm")[0].reset()
    if(event.target.id) {

        $.ajax({
            url: '/admin/posts/getpost/' + event.target.id,
            method: 'get'
        }).done(function(res) {
            if(res.success) {
                $("#id").val(res.data.post.id)
                $("#title").val(res.data.post.title)
                $("#link").val(res.data.post.link)
                $("#content").val(res.data.post.content)
                $("#summary").val(res.data.post.summary)
                $("#enabled").prop('checked', res.data.post.enabled)
                renderTags(res.data.post.tags)
            }
        })
     }
})

$(document).on('click', '.copyButton', function(event) {
    if(event.target.id) {

        $.ajax({
            url: '/admin/posts/copypost/' + event.target.id,
            method: 'get'
        }).done(function(res) {
            if(res.success) {
                getPosts()
            }
        })
     }
})