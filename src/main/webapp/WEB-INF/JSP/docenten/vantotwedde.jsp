<%@page contentType='text/html' pageEncoding='UTF-8' session='false'%>
<%@taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@taglib uri='http://java.sun.com/jsp/jstl/fmt' prefix='fmt'%>
<%@taglib uri='http://vdab.be/tags' prefix='v'%>
<!doctype html>
<html lang='nl'>
<head>
<v:head title='Docenten van tot wedde' />
<style>
td:first-child, td:last-child {
	text-align: right;
}
</style>
</head>
<body>
	<v:menu />
	<h1>Docenten van tot wedde</h1>
	<table>
		<thead>
			<tr>
				<th>Nummer</th>
				<th>Naam</th>
				<th>Wedde</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items='${docenten}' var='docent'>
				<tr>
					<td>${docent.id}</td>
					<td>${docent.naam}</td>
					<td><fmt:formatNumber value='${docent.wedde}'
							minFractionDigits='2' maxFractionDigits='2' /></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</body>
</html>