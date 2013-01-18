<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<script type="text/javascript" src="<c:url value="/resources/js/component/category_tree.js" />"></script>

<ul id="category_tree">
	<li><input type="checkbox"><label>Node 1</label>
		<ul>
			<li><input type="checkbox"><label>Node 1.1</label>
				<ul>
					<li><input type="checkbox"><label>Node 1.1.1</label>
				</ul>
		</ul>
		<ul>
			<li><input type="checkbox"><label>Node 1.2</label>
				<ul>
					<li><input type="checkbox"><label>Node 1.2.1</label>
					<li><input type="checkbox"><label>Node 1.2.2</label>
					<li><input type="checkbox"><label>Node 1.2.3</label>
						<ul>
							<li><input type="checkbox"><label>Node 1.2.3.1</label>
							<li><input type="checkbox"><label>Node 1.2.3.2</label>
						</ul>
					<li><input type="checkbox"><label>Node 1.2.4</label>
					<li><input type="checkbox"><label>Node 1.2.5</label>
					<li><input type="checkbox"><label>Node 1.2.6</label>
				</ul>
		</ul>
	<li><input type="checkbox"><label>Node 2</label>
		<ul>
			<li><input type="checkbox"><label>Node 2.1</label>
				<ul>
					<li><input type="checkbox"><label>Node 2.1.1</label>
				</ul>
			<li><input type="checkbox"><label>Node 2.2</label>
				<ul>
					<li><input type="checkbox"><label>Node 2.2.1</label>
					<li><input type="checkbox"><label>Node 2.2.2</label>
					<li><input type="checkbox"><label>Node 2.2.3</label>
						<ul>
							<li><input type="checkbox"><label>Node 2.2.3.1</label>
							<li><input type="checkbox"><label>Node 2.2.3.2</label>
						</ul>
					<li><input type="checkbox"><label>Node 2.2.4</label>
					<li><input type="checkbox"><label>Node 2.2.5</label>
					<li><input type="checkbox"><label>Node 2.2.6</label>
				</ul>
		</ul>
</ul>