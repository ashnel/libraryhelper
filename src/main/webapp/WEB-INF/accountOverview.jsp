<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Account Overview</title>
	<link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
	<!-- Compiled and minified CSS -->
	<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/materialize/1.0.0-beta/css/materialize.min.css">
    <style>
    	#green-circle {
		border-radius: 50%;
		width: 20px;
		height: 20px; 
		background-color: green;
		display: inline-block;
		vertical-align: top;
    	margin-top: 3.5%;
		/* width and height can be anything, as long as they're equal */
		}
		#red-circle {
		border-radius: 50%;
		width: 20px;
		height: 20px; 
		background-color: red;
		display: inline-block;
		vertical-align: top;
    	margin-top: 3.5%;
		/* width and height can be anything, as long as they're equal */
		}
		#book-info {
		display: inline-block;
		}
		h1, h2, h3, h4, h5, h6 {
			line-height: 85%;
		}
		.title {
			font-weight: bold;
		}
		#late-fee {
			font-size: xx-small;
    		color: red;
    		font-weight: bold;
		}
    </style>
    <link href="c3.css" rel="stylesheet">
    <script src="d3.min.js" charset="utf-8"></script>
	<script src="c3.min.js"></script>
</head>
<body>
    <!-- Navbar goes here -->
	 <nav>
	    <div class="nav-wrapper">
	      <form method="GET" action="/search">
	        <div class="input-field">
	          <input name="searchtext" id="search" type="search" required>
	          <label class="label-icon" for="search"><i class="material-icons">search</i></label>
	          <i class="material-icons">close</i>
	        </div>
	      </form>
	    </div>
	  </nav>

    <!-- Page Layout here -->
    <div class="row">
      <div class="col s12 m3 l2"> <!-- Note that "m4 l3" was added -->
      	<h4>Welcome, <c:out value="${currentUser.firstName}"></c:out></h4>
      	<form id="logoutForm" method="POST" action="/logo">
	        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
	        <input class="btn waves-effect waves-light col l12" id="submit" type="submit" value="Logout" /><br><br>
    	</form>
    	<form id="logoutForm" method="POST" action="/account">
	        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
	        <input class="btn waves-effect waves-light col l12" id="submit" type="submit" value="Account Overview" /><br><br>
    	</form>
    	<form id="logoutForm" method="POST" action="/">
	        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
	        <input class="btn waves-effect waves-light col l12" id="submit" type="submit" value="Dashboard" />
    	</form>
        <div>
    	<!-- Current Books -->
    	<c:forEach items="${checkedBooks}" var="checkedBook">
		    <div>
				<jsp:useBean id="now" class="java.util.Date"/>
				
				<c:if test="${checkedBook.returnDate > now}">
					<div class="col l1" id="green-circle"></div>
				</c:if>
				<c:if test="${checkedBook.returnDate < now}">
					<div class="pulse col l1" id="red-circle"></div>
				</c:if>
		    
		    	
		    	<div class="col l11" id="book-info">
		    		<h6 class="title">${checkedBook.book.title}</h6>
		    		<h6>By ${checkedBook.book.author}</h6>
			    	<p>Checked Out: <fmt:formatDate pattern = "MMM dd, yyyy" value = "${checkedBook.checkoutDate}" /></p>
			    	<p>Due On: <fmt:formatDate pattern = "MMM dd, yyyy" value = "${checkedBook.returnDate}" /></p>
			    	<c:if test="${checkedBook.returnDate < now}">
						<p id="late-fee">This item is overdue. Please return it as soon as possible or you will incur fees of 15-25 cents per day.</p>
					</c:if>
		    	</div>
		    </div>
		</c:forEach>
    </div>
      </div>	

      <div class="col s12 m9 l10"> <!-- Note that "m8 l9" was added -->

        <div id="all_books">
        
        <ul class="collection">
	    	<li class="collection-item">
	    	<div id="line"></div>
	    	</li>
	    	
	    	<li class="collection-item">
	    	<div style="display: inline-block" id="donut"></div>
	    	</li>
	    	
	    	<li class="collection-item">
	    	<div id="pie"></div>
	    	</li>
			</ul>
	    </div>
      </div>
    </div>
    <script>
    var chart = c3.generate({
    	bindto: '#line',
        data: {
            x: 'x',
            columns: [
                ['x', '2015-01-01', '2016-01-01', '2017-01-01', '2018-01-01'],
                ['Total Books Checked Out', 7, 23, 12, 16]
            ]
        },
        axis : {
            x : {
                type : 'timeseries',
                tick: {
                    format: function (x) { return x.getFullYear(); }
                  //format: '%Y' // format string is also available for timeseries data
                }
            }
        }
    });
    
    var chart = c3.generate({
    	bindto: '#donut',
        data: {
            columns: [
                ['Educational', 12],
                ['Fantasy', 6],
                ['Mystery', 3],
                ['Science Fiction', 9],
                ['Nonfiction', 2],
            ],
            type : 'donut',
            onclick: function (d, i) { console.log("onclick", d, i); },
            onmouseover: function (d, i) { console.log("onmouseover", d, i); },
            onmouseout: function (d, i) { console.log("onmouseout", d, i); }
        },
        donut: {
            title: "Previous Books By Genre"
        }
    });
    
    var chart = c3.generate({
    	bindto: '#pie',
        data: {
            // iris data from R
            columns: [
                ['Returned Late', 15],
                ['Returned On Time', 32],
            ],
            type : 'pie',
            onclick: function (d, i) { console.log("onclick", d, i); },
            onmouseover: function (d, i) { console.log("onmouseover", d, i); },
            onmouseout: function (d, i) { console.log("onmouseout", d, i); }
        }
    });
    </script>
</body>
</html>