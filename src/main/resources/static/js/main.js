/**
 * Toggles the add recipe form
 */
function addRecipe() {
    $('.center').empty(); // Clear previous results before appending new ones
    $('#addRecipeForm').toggle('show');
}

function showMore(){{
    console.log("show more")
    hideComponent('.recipe')
    $('#showMoreRecipe').toggle('show');
}}

function closeShowMore(){
    $('#showMoreRecipe').toggle('show');
    $('.recipe').css('display', 'block');
}


$(document).on('click', '#showMore', function(recipeData) {
    console.log("on click: show more")
    let recipeDiv = $(this).closest('.recipe');
    let title = recipeDiv.find('#title span').text();
    document.querySelector("#popUpTitle span").innerText = title;
    let ingredients = recipeDiv.find('#showMoreIngredients span').text();
    document.querySelector("#showMoreIngredients").innerText = ingredients;
    let prepTime = recipeDiv.find('#prepTime span').text();
    document.querySelector("#showMorePrepTime").innerText = prepTime;
    let cookTime = recipeDiv.find('#cookTime span').text();
    document.querySelector("#showMoreCookTime").innerText = cookTime;
    let totalTime = recipeDiv.find('#totalTime span').text();
    document.querySelector("#showMoreTotalTime").innerText = cookTime;
    let servings = recipeDiv.find('#servings span').text();
    document.querySelector("#showMoreServings").innerText = cookTime;
});

$(function() {
    var debounceTimeout = null

    $('#searchInput').on('input', function() {
        clearTimeout(debounceTimeout)
        console.log("#searchInput")
        debounceTimeout = setTimeout(() => {
            getRecipe(this.value.trim())
        }, 1500);
    })
});

/**
 * Uses the recipe title provided by the user to search and show the corresponding movies
 */
function getRecipe(recipe) {

    console.log("In get recipes")

    if (!recipe) return

    onBeforeSend()
    fetchRecipeFromApi(recipe)
}

/**
 * Actions to take before the search query is sent,
 * like hiding any previous information about the movie
 */
function onBeforeSend() {
    hideComponent('.recipe')
    hideNotFound()
}

/**
 * Hides a component identified by the provided jquery selector
 */
function hideComponent(selector) {
    return $(selector).css('display', 'none');
}

/**
 * Fetches the recipe data
 * This function defines handling for both successful and failed(recipe not found, api unavailable etc.) responses
 */
function fetchRecipeFromApi(recipe) {
    let xhr = new XMLHttpRequest()

    xhr.open('GET', `http://localhost:8080/recipes/search?title=${recipe}`, true)
    xhr.timeout = 5000
    xhr.ontimeout = (e) => onApiError(e)
    xhr.onreadystatechange = function() {
        if (xhr.readyState === 4) {
            if (xhr.status === 200) {
                handleResults(JSON.parse(xhr.responseText))
            } else {
                console.log("xhr.status: " + xhr.status)
                showNotFound()
            }
        }
    }
    xhr.send()
}

document.getElementById("logout").addEventListener("click", function() {
// URL of the Spring Boot backend endpoint

});

/**
 * Actions to take if the Movie API fails to respond.
 */
function onApiError() {
    // hideComponent('#waiting')
    // showError()
}

/**
 * Determines if the recipe is found
 * If the recipe is found, the API response is transformed and then shown.
 * Otherwise, show a not found message.
 */
function handleResults(response) {
    console.log("In handle results")
    console.log(response.length)
    if (response === null || response.length === 0) { // if the response is null, return a message
        showNotFound()

    } else {
        console.log(response)
        buildRecipe(response)
    }
}

function buildRecipe(response) {
    if (!Array.isArray(response) || response.length === 0) {
        console.error("Invalid response data: Empty array.");
        return;
    }

    response.forEach(recipeData => {
        let newRecipe = $('.recipe').first().clone().removeClass('hidden').addClass('mb-3');

        newRecipe.find('[id]').each((index, item) => {
            let $item = $(item);
            let itemId = item.id; // Get the ID of the element
            let metadataValue = recipeData[itemId]; // Get the corresponding value from recipeData

            // If it's an image, update 'src' using filePath
            if ($item.is('img')) {
                buildImageURL(recipeData.filePath, recipeData.name, $item)
            }
            // For other elements, update their content
            else {
                let valueElement = $item.children('span');
                if (metadataValue !== undefined) {
                    valueElement.length ? valueElement.text(metadataValue) : $item.text(metadataValue);
                } else {
                    console.warn(`No matching data found for ${itemId} in response.`);
                }
            }
        });

        newRecipe.css('display', 'block').appendTo($('.center'));
    });
}

function buildImageURL(imgFilepath, recipeName,$item) {
    if (imgFilepath) { // Use filePath to set the image source
        console.log(imgFilepath)
        let imageUrl = `http://localhost:8080/CF6/recipes/${imgFilepath}`
        $item.attr('src', imgFilepath);
        console.log("Constructed Image URL:", imageUrl);
        $item.attr('src', imageUrl);
    } else {
        console.warn(`No filePath found for recipe: ${recipeName}`);
    }
}

function showNotFound() {
    $('.not-found').clone().removeClass('hidden').appendTo($('.center'))
}

function hideNotFound() {
    $('.center').find('.not-found').remove()
}

const inputFile = document.querySelector('#file');
const imgArea = document.querySelector('.img-area');

$('.select-image').on('click', function() {
    inputFile.click();
});

$('#addRecipe').on('click', function() {
    $('main').hide(); // This will hide the <main> element
});

$('#file').on('change', function() {
    const image = this.files[0]
    const reader = new FileReader();
    reader.onload = () => {
        const allImg = imgArea.querySelectorAll('img');
        allImg.forEach(item => item.remove());
        const imgUrl = reader.result;
        const img = document.createElement('img');
        img.src = imgUrl;
        imgArea.appendChild(img);
        imgArea.classList.add('active');
        imgArea.dataset.img = image.name;
    }
    reader.readAsDataURL(image);
});

