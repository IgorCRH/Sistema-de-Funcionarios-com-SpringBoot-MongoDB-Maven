// Função para obter a lista de funcionários do servidor e atualizar a interface do usuário
function getEmployees() {
    fetch("http://localhost:8080/api/employees")
        .then(response => {
            if (!response.ok) {
                throw new Error(`HTTP error! Status: ${response.status}`);
            }
            return response.json();  // Agora estamos usando json() para obter diretamente um objeto JSON
        })
        .then(data => {
            console.log("Lista de funcionários:", data);
            updateEmployeeList(data);
        })
        .catch(error => {
            console.error("Erro ao obter funcionários:", error);
        });
}

function addEmployee() {
    const name = document.getElementById("name").value;
    const salary = parseFloat(document.getElementById("salary").value);
    const position = document.getElementById("position").value;
    const hoursWorked = parseInt(document.getElementById("hoursWorked").value);

    const queryParams = new URLSearchParams({
        name: name,
        salary: salary,
        position: position,
        hoursWorked: hoursWorked
    });

    fetch(`http://localhost:8080/api/employees?${queryParams}`, {
        method: "POST"
    })
    .then(response => {
        if (response.status >= 300 && response.status < 400) {
            // Se a resposta é um redirecionamento, chama getEmployees para atualizar a lista
            getEmployees();
        } else if (!response.ok) {
            // Se não é um redirecionamento, verifica se a resposta é bem-sucedida
            throw new Error(`HTTP error! Status: ${response.status}`);
        } else {
            // Se a resposta é bem-sucedida, faz o parse do JSON
            return response.text();
        }
    })
    .then(data => {
        if (data) {
            console.log("Funcionário adicionado:", data);
            getEmployees();
        }
    })
    .catch(error => {
        console.error("Erro ao adicionar funcionário:", error.message);
    });
}

// Chama o método para obter funcionários inicialmente
getEmployees();

//Função para pesquisar um funcionário por nome
function searchEmployee() {
    const searchName = document.getElementById("searchName").value;

    fetch("http://localhost:8080/api/employees/search", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({ name: searchName })
    })
    .then(response => {
        if (!response.ok) {
            throw new Error(`HTTP error! Status: ${response.status}`);
        }
        return response.json();
    })
    .then(data => {
        console.log("Resultado da pesquisa:", data);
        alert("Resultado da pesquisa: " + JSON.stringify(data));

        // Se houver algum resultado, obtenha as informações completas pelo ID
        if (data && data.length > 0) {
            const employeeId = data[0].id;
            return fetch(`http://localhost:8080/api/employees/${employeeId}`);
        } else {
            throw new Error("Nenhum funcionário encontrado com o nome fornecido.");
        }
    })
    .then(response => {
        if (!response.ok) {
            throw new Error(`HTTP error! Status: ${response.status}`);
        }
        return response.json();
    })
    .then(employee => {
        console.log("Informações completas do funcionário:", employee);
        updateEmployeeList([employee]);
    })
    .catch(error => {
        console.error("Erro ao pesquisar funcionário:", error);
    });
}


// Função para atualizar a lista de funcionários na interface do usuário
function updateEmployeeList(employees) {
    const employeeList = document.getElementById("employeeList");
    clearEmployeeList();

    employees.forEach(employee => {
        const listItem = document.createElement("li");
        listItem.className = "list-group-item";
        listItem.setAttribute("data-id", employee.id);
        listItem.textContent = `Nome: ${employee.name} - Salário: ${employee.salary} - Cargo: ${employee.position} - Horas Trabalhadas: ${employee.hoursWorked}`;
        listItem.addEventListener("click", handleEmployeeClick);
        employeeList.appendChild(listItem);
    });
}

// Função para limpar a lista de funcionários
function clearEmployeeList() {
    const employeeList = document.getElementById("employeeList");
    while (employeeList.firstChild) {
        employeeList.removeChild(employeeList.firstChild);
    }
}

// Função para lidar com o clique em um funcionário na lista
function handleEmployeeClick(event) {
    const selectedEmployee = document.querySelector(".list-group-item.active");
    if (selectedEmployee) {
        selectedEmployee.classList.remove("active");
    }

    const listItem = event.currentTarget;
    listItem.classList.add("active");
}

// Função para atualizar o salário de um funcionário
function updateSalary() {
    const newSalary = document.getElementById("newSalary").value;
    const selectedEmployee = document.querySelector(".list-group-item.active");

    if (selectedEmployee) {
        const employeeId = selectedEmployee.getAttribute("data-id");

        fetch(`http://localhost:8080/api/employees/update-salary?id=${employeeId}&newSalary=${newSalary}`, {
            method: "POST"
        })
            .then(response => {
                if (response.status >= 300 && response.status < 400) {
                    throw new Error(`HTTP error! Status: ${response.status}`);
                }
                return response.text();
            })
            .then(data => {
                console.log("Salário atualizado com sucesso:", data);
                getEmployees(); // Atualiza a lista após atualizar o salário
            })
            .catch(error => {
                console.error("Erro ao atualizar salário:", error.message);
            });
    } else {
        console.log("Nenhum funcionário selecionado.");
    }
}

function removeEmployee() {
    const selectedEmployee = document.querySelector(".list-group-item.active");

    if (selectedEmployee) {
        const employeeId = selectedEmployee.getAttribute("data-id");

        fetch(`http://localhost:8080/api/employees/${employeeId}/delete`, {
            method: "DELETE"
        })
            .then(response => {
                if (response.status >= 300 && response.status < 400) {
                    throw new Error(`HTTP error! Status: ${response.status}`);
                }
                return response.text();
            })
            .then(data => {
                console.log("Funcionário removido com sucesso:", data);
                getEmployees();
            })
            .catch(error => {
                console.error("Erro ao remover funcionário:", error.message);
            });
    } else {
        console.log("Nenhum funcionário selecionado.");
    }
}


// Inicializa a lista de funcionários ao carregar a página
getEmployees();