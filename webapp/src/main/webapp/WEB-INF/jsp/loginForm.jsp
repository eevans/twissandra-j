<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Register a New User</title>
</head>
<body>

<h1>Login</h1>

	    <div class="grid_9 alpha">
        <div class="auth grid_4 prefix_1 alpha" id="signin">
            <h2 class="grid_4">Sign In</h2>
            <form method="POST">
                <ul>
                    {{ login_form.as_ul }}
                </ul>
                <input type="hidden" name="kind" value="login" />
                {% if next %}
                    <input type="hidden" name="next" value="{{ next }}" />
                {% endif %}
                <input type="submit" value="Sign In" />
            </form>
        </div>

        <div class="auth grid_4 omega" id="signup">
            <h2 class="grid_4">Sign Up</h2>
            <form method="POST">
                <ul>
                    {{ register_form.as_ul }}
                </ul>
                <input type="hidden" name="kind" value="register" />
                {% if next %}
                    <input type="hidden" name="next" value="{{ next }}" />
                {% endif %}
                <input type="submit" value="Sign Up" />
            </form>
        </div>
    </div>
{% endblock %}
~

</body>
</html>