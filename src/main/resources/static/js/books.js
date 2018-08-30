const FORM_ID = '#search';
const CONTAINER_ID = '#books';

const FORM_INPUT_STRING = `${FORM_ID} input[name=query]`;

const createDiv = (book) => {
	const el = $('<div></div>');
	return {
		book,
		el
	};
};

const addNameHeader = ({ book, el}) => {
	el.append('<h2></h2>');
	return {
		book,
		el
	};
};

const addAnchorToHeader = ({ book, el}) => {
	const anchorEl = $('<a></a>');
	anchorEl.attr('href', `/${book.id}`);
	anchorEl.text(book.name);
	$(el).children('h2').append(anchorEl);
	return {
		book,
		el
	}
};

const addAuthorHeader = ({book, el}) => {
	const headerEl = $('<h3></h3>');
	headerEl.text(book.author);
	$(el).append(headerEl);
	return {
		book,
		el
	}
}

const addDescriptiptionParagraph = ({book, el}) => {
	const paragraphEl = $('<p></p>');
	paragraphEl.text(book.description);
	$(el).append(paragraphEl);
	return el;
}

const appendElements = (el) => {
	$(CONTAINER_ID).append(el);
}

const createElements = (data) => {
	
	$(CONTAINER_ID).empty();
	
	const elements = data
		.map(createDiv)
		.map(addNameHeader)
		.map(addAnchorToHeader)
		.map(addAuthorHeader)
		.map(addDescriptiptionParagraph)
		
	if(elements.length > 0) {
		elements.forEach(appendElements);
	} else {
		$(CONTAINER_ID).append('<p>No books!</p>');
	}
}

const fetchAndCreate = (options) => {
	$.ajax(options)
	.done(createElements)
	.fail(console.error);
} 

const handleSubmit = (event) => {
	
	$('#books').html('<p>fetching...</p>');

	const query = $(FORM_INPUT_STRING).val();
	
	fetchAndCreate({
		url: '/api/search',
		dataType: 'json',
		data: {
			query,
		}
	});
	
	event.preventDefault();
};

$(() => {
	// handle input
	$(FORM_ID).submit(handleSubmit);
	$(FORM_ID).keyup(handleSubmit);
	
	// fill books
	fetchAndCreate({
		url: '/api/list',
		dataType: 'json',
	});
});