<c:choose>
	<c:when test="${coms.size() > 0 }">
		<c:forEach items="${coms}" var="com">
			<div class="pdLeft">
				<p class="comHead">
					<span class="glyphicon glyphicon-hand-right"></span>&nbsp;<c:out value="${com.value.titre}"/>&nbsp;
					<span class="glyphicon glyphicon-user"></span>&nbsp;<c:out value="${com.value.opticien}"/>&nbsp;
					<span class="glyphicon glyphicon-time"></span>&nbsp;<c:out value="${com.value.date_com}"/>&nbsp;
				</p>
				<p class="comContent">
					<span class="glyphicon glyphicon-comment alert alert-warning"></span>&nbsp;<c:out value="${com.value.com}"/>
				</p>
			</div>
		</c:forEach>
	</c:when>
	<c:otherwise>
		<p class="alert alert-info">Aucun commentaire</p>
	</c:otherwise>
</c:choose>