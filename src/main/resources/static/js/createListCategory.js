async function createListCategory(){

    const response = await fetch("api/category", {

        method: "GET",
        headers: {
            'Accept': 'application/json'
        }

    });

    const listCategory = await response.json();

    const selectList = document.getElementById("list_category");
    const divFilterContainer = document.getElementById("filterAdvt");

    for (const category of listCategory){

        if (category.idParent == null){

            const optgroup = document.createElement("optgroup");
            optgroup.label = category.name;

            selectList.appendChild(optgroup);

            if (divFilterContainer != null) {

                const option = document.createElement("option");
                option.value = category.id;
                option.textContent = "(Выбрать всю категорию: " + category.name + ")";

                optgroup.appendChild(option);

            }

            for (const descendantCategory of listCategory){

                if (descendantCategory.idParent != null && category.id === descendantCategory.idParent){

                    const option = document.createElement("option");
                    option.value = descendantCategory.id;
                    option.textContent = descendantCategory.name;

                    optgroup.appendChild(option);

                }

            }

        }

    }

}

createListCategory()