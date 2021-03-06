<html>
    <head>
        <meta name="layout" content="layout"/>
    </head>
    <body>

        <div class="container-fluid">
        <div class="row">
        <div class="col">

        <g:render template="/shared/adminnav"/>

        <h1 class="display-4 my-3">Tags</h1>

        <g:if test="my-3 w-50 ${flash.title}">
            <div class="${flash.class}">
                <h4>${flash.title}</h4>
                <p>${flash.message}</p>
            </div>
        </g:if>

        <table class="table my-3">
            <thead>
            <tr>
            <th>name</th>
            <th>shortUrl</th>
            <th>description</th>
            <th>enabled</th>
            <th>image</th>
            <th>actions</th>
            </tr>
            </thead>
            <tbody>
            <g:each in="${tags}" var="tag">
                 <tr>
                 <td>
                 ${tag.name}
                 </td>
              <td>
              <a href="/tags/${tag.shortUrl}">${tag.shortUrl}</a>
              </td>
                 <td>
                 ${tag.description.take(admin_take_size)}
                 </td>
              <td>
              ${tag.enabled}
              </td>
                 <td>
                 <img style="max-width: 200px;" src="data:${tag.imageContentType};base64,${tag.imageBytes.encodeBase64()}"/>
                 </td>
                     <td>
                         <span onclick="window.location.href='/admin/tags?id=${tag.id}'" class="mr-4"><i class="fas fa-edit"></i></span>
                         <span onclick="confirmDeletion(${tag.id})" class="mr-4"><i class="fas fa-trash"></i></span>
                     </td>
                 </tr>
             </g:each>
            </tbody>
        </table>

        <form name="tag" method="post" action="/admin/tags/addEdit" class="border border-rounded p-4" enctype="multipart/form-data">

        <input type="hidden" name="id" value="${id}"/>

        <div class="form-group">
            <label for="name"><h5>Name</h5></label>
            <input type="text" class="form-control form-control-lg" name="name" id="name" value="${name}" required placeholder="Tag name">
        </div>

        <div class="form-group">
            <label for="description"><h5>Description</h5></label>
            <textarea rows="4" class="form-control form-control-lg" id="description" name="description" required placeholder="Tag description">${description}</textarea>
        </div>

          <div class="form-check my-3">
            <input type="checkbox" class="form-check-input" id="enabled" name="enabled" ${enabled == true ? "checked" : ""}>
            <label class="form-check-label" for="enabled">Enabled</label>
          </div>

        <div class="form-group">
            <label for="shortUrl"><h5>Short Url</h5></label>
            <input type="text" class="form-control form-control-lg" id="shortUrl" name="shortUrl" value="${shortUrl}" required placeholder="shortcut-link-for-seo"/>
        </div>


        <g:if test="${imageName}">
            <div class"my-4">
            <h5>Current Image</h5>
            <p class="lead">Loading a new image will overwrite this existing image, otherwise it will remain the image associated with this tag.</p>
            <img class="img-rounded img-fluid" style="max-width: 600px;" src="data:${imageContentType};base64,${imageBytes.encodeBase64()}"/>
            </div>
        </g:if>

          <div class="form-group">
            <label for="image"><h5>Tag Image</h5></label>
            <input type="file" class="form-control-file" id="image" name="image" ${id == null ? "required" : ""}>
          </div>

    <g:if test="${lastUpdated}">
        <div class="m-3 p-3">
          <h3>Last Updated: <span class="text-muted"><g:formatDate date="${lastUpdated}" type="datetime" style="MEDIUM"/></h3></span>
          <h3>Date Created: <span class="text-muted"><g:formatDate date="${dateCreated}" type="datetime" style="MEDIUM"/></h3></span>
        </div>
        </g:if>

            <div class="text-center my-4">
                <input type="submit" class="btn btn-primary btn-lg" value="Submit"/>
            </div>
        </form>

</div>
</div>
</div>

<script>
var confirmDeletion = function(id) {
    $('#tagId').html(id)
    $('#deleteConfirmationModal').modal('toggle')
}
</script>

<!-- Modal -->
<div class="modal fade" id="deleteConfirmationModal" tabindex="-1" role="dialog">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title">Confirm Tag Deletion.</h5>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      <div class="modal-body">Delete your tag id #<span id="tagId"></span>?</div>
      <div class="modal-footer">
        <button type="button" class="btn btn-secondary" data-dismiss="modal">Nope.</button>
        <button type="button" class="btn btn-primary" onclick="window.location.href='/admin/tags/deleteTag?id='+$('#tagId').text()">Yup!</button>
      </div>
    </div>
  </div>
</div>


    </body>
</html>
