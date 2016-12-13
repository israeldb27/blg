<%
		if (request.getAttribute("redirectURL") != null) {
	%>
					<div>
						<a href=<%=(String) request.getAttribute("redirectURL")%>>Redirect
							to PayPal to approve the payment</a>
					</div>
					<%
		}
	%>
	
