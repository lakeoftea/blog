<html>
    <head>
        <meta name="layout" content="layout"/>
		<asset:javascript src="account.indexForAdmin.js"/>
    </head>
    <body>

        <g:applyLayout name="navbar-admin">
        </g:applyLayout>

        <h2>Accounts</h2>

      <g:if test="${flash.alert}">
        <div class="alert alert-danger alert-dismissible evaluate(flash.class)" role="alert">
          <button type="button" class="close" data-dismiss="alert" aria-label="Close">
            <span aria-hidden="true">&times;</span>
          </button>
        ${flash.message}
        </div>
      </g:if>

        <table class="table">
            <thead class="thead-inverse">
                <tr>
                    <th>Id</th>
                    <th>Username</th>
                    <th>Full Name</th>
                    <th>Email Address</th>
                    <th>Role</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                <g:each in="${accounts}" var="account">
                    <tr>
                        <th>${account.id}</th>
                        <td>${account.username}</td>
                        <td>${account.fullName}</td>
                        <td>${account.emailAddress}</td>
                        <td>${account.role}</td>
                        </td>
                        <td>
                            <span accountId="${account.id}" class="editAccountIcon octicon octicon-pencil"></span>
							<span accountId="${account.id}" class="deleteAccountIcon octicon octicon-x"></span>
                        </td>
                    </tr>
                </g:each>
            </tbody>
        </table>

    <div class="alert alert-success alert-dismissable fade">
      <button type="button" class="close" data-dismiss="alert" aria-label="Close">
        <span aria-hidden="true">&times;</span>
      </button>
    Account succesfully edited!
    </div>

	<p>
		<input id="createAccountButton" type="button" class="btn btn-primary" value="Create Account">
	</p>

    <form id="updateAccountForm">

    <input type="hidden" id="id">

    <div class="form-group">
    <label for="username">Username</label>
    <input disabled type="text" class="form-control" id="username" required>
    </div>

    <div class="form-group">
    <label for="fullname">Full name</label>
    <input type="text" class="form-control" id="fullname" required>
    </div>

    <div class="form-group">
    <label for="emailaddress">Email Address</label>
    <input type="text" class="form-control" id="emailaddress" required>
    </div>

    <div class="form-group">
      <label for="role">Role</label>
      <select id="role" class="form-control">
        <g:each in="${roles}" var="role">
          <g:if test="${role == session.account.role}">
          <option selected>${role}</option>
          </g:if>
          <g:else>
          <option>${role}</option>
          </g:else>
        </g:each>
      </select>
    </div>

    <input id="resetPasswordButton" type="button" class="btn btn-primary" value="Reset Password">

    <p>
    <div id="resetPasswordFields">
    <p>

    <div class="form-group">
    <label for="password">Password</label>
    <input type="password" class="form-control" id="password" required>
    </div>

    <div class="form-group">
    <label for="passwordconfirmation">Confirm Password</label>
    <input type="password" class="form-control" id="passwordconfirmation" required>
    </div>

    </p> 
    </div>
    </p>

        <div id="successfulEdit" class="alert alert-success alert-dismissible collapse" role="alert">
          <button type="button" class="close" data-dismiss="alert" aria-label="Close">
            <span aria-hidden="true">&times;</span>
          </button>
        Account successfully edited!
        </div>

    <input id="submitButton" type="submit" class="btn btn-primary btn-block" value="Update Account">

    </form>

    </body>
</html>