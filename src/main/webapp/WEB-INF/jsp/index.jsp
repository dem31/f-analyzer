<!doctype html>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
<head>
  <meta charset="utf-8">
  <title>Spring MVC and Hibernate Template</title>

  <meta content="IE=edge,chrome=1" http-equiv="X-UA-Compatible">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">

  <link href="//netdna.bootstrapcdn.com/bootstrap/2.3.2/css/bootstrap.min.css" rel="stylesheet">

  <style type="text/css">
  </style>
  <script type="text/javascript">
  </script>
</head>



<div class="container" id="getting-started">
<div class="row">
<div class="span8 offset2">

<div class="page-header">
  <h1>Get started with your Spring MVC and Hibernate Application</h1>
</div>

<div class="tab-content">


<div id="eclipse-instructions" class="instructions tab-pane active">
  <a name="using-eclipse"></a>

  <div class="alert alert-warn">If you already created this app from the Heroku Eclipse Plugin, proceed to <a
      href="#step3">Step 3</a>.
    The following steps depends on the Heroku Eclipse plugin. If you do not have the Heroku Eclipse plugin installed and
    configured,
    follow a <a href="https://devcenter.heroku.com/articles/getting-started-with-heroku-eclipse#installation-and-setup"
                target="_blank">step-by-step guide</a>
    on Dev Center to install the plugin and
    configure the plugin in Eclipse.
  </div>

  <h2>Step 1.Configure Heroku Eclipse preferences</h2>
  <ol>
    <li>Open <code>Eclipse</code><i class="icon-chevron-right"></i><code>Preferences</code></li>
    <li>Select <code>Heroku</code></li>
    <li>Enter your <code>Email</code> and <code>Password</code></li>
    <li>Click <code>Login</code>. If your login was successful, your Heroku API key would be populated in the
      <code>API Key</code> field.<br/>

      <div class="modal hide" id="apiPreferences">
        <div class="modal-header">
          <a class="close" data-dismiss="modal"><i class="icon-remove"></i></a>

          <h3>Setup API Key</h3>
        </div>
        <div class="modal-body">
          <img src='https://template-app-instructions-screenshots.s3.amazonaws.com/eclipse/heroku_preferences.png'
               alt="setup api key"/>
        </div>
      </div>
                    <span class="screenshot">
                      <a href="#apiPreferences" data-toggle="modal">
                        <img
                            src='https://template-app-instructions-screenshots.s3.amazonaws.com/eclipse/heroku_preferences.png'
                            alt="setup api key" width="100">
                        <i class="icon-zoom-in"></i>
                      </a>
                    </span>
    </li>
    <li>In the SSH Key section, click <code>Generate</code> if you need to generate a new key.
      If you have previously generated and saved a SSH key is automatically loaded from the default location. If it is
      not
      in the default location, click <code>Load SSH Key</code><br/>
    </li>
    <li>Click on <code>Add</code> to add your SSH Key to Heroku
      <div class="modal hide" id="sshkeyadd">
        <div class="modal-header">
          <a class="close" data-dismiss="modal"><i class="icon-remove"></i></a>

          <h3>Add SSH Key to Heroku</h3>
        </div>
        <div class="modal-body">
          <img src='https://template-app-instructions-screenshots.s3.amazonaws.com/eclipse/heroku_ssh_add.png'
               alt="Add SSH Key to Heroku"/>
        </div>
      </div>
	                    <span class="screenshot">
	                      <a href="#sshkeyadd" data-toggle="modal">
                          <img
                              src='https://template-app-instructions-screenshots.s3.amazonaws.com/eclipse/heroku_ssh_add.png'
                              alt="Add SSH Key to Heroku" width="100"/>
                          <i class="icon-zoom-in"></i>
                        </a>
	                    </span>
    </li>
  </ol>

  
 

  



</div>
</div>
</div>
</div>

<!-- end tab content -->
</div>


<script src="//code.jquery.com/jquery-1.9.1.min.js"></script>
<script src="//netdna.bootstrapcdn.com/bootstrap/2.3.2/js/bootstrap.min.js"></script>
</body>
</html>
