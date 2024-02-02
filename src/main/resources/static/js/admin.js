'use strict'

window.addEventListener("DOMContentLoaded", getAllUsers);

//аутентификация и авторизация
async function getAuthUser() {
    let auth = await fetch("userApi/auth");
    let user = await auth.json();

    document.getElementById('principal').insertAdjacentHTML("beforeend", `
        <span>${user.email}</span>
        <span> с ролями: </span>
        <span id="principalID${user.id}"></span>
    `)
    getRole(user, "principalID");

    document.getElementById('userAuth').insertAdjacentHTML('beforeend', `
        <tr>
            <td>${user.id}</td>
            <td>${user.firstName}</td>
            <td>${user.lastName}</td>
            <td>${user.email}</td>
            <td>${user.username}</td>
            <td id="rolesID${user.id}"></td>
        </tr>`)
    getRole(user, "rolesID");
}
getAuthUser();

//получение данных из базы
async function getAllUsers() {
    let tbody = $('#allUsers');
    tbody.empty();
    let res = await fetch('api/users');
    let users = await res.json();

    users.forEach(user => userToHTML(user));
}

//отображение таблицы с данными на странице
function userToHTML(user) {
    let userList = document.getElementById('allUsers');
    let htmlId = document.getElementById('user' + user.id)

    if (!htmlId) {
        userList.insertAdjacentHTML('beforeend', `
            <tr id="user${user.id}" class="align-middle">
                <td>${user.id}</td>
                <td>${user.firstName}</td>
                <td>${user.lastName}</td>
                <td>${user.email}</td>
                <td>${user.username}</td>
                <td id="rolesID${user.id}"></td>
                <td>
                    <button type="button" class="btn btn-success" data-bs-toggle="modal" th:data-bs-target="#edit" onclick="modalEdit(${user.id})">
                        Редактировать
                    </button>
                </td>
                <td>
                    <button type="button" class="btn btn-danger" data-bs-toggle="modal" th:data-bs-target="#delete" onclick="modalDelete(${user.id})">
                        Удалить
                    </button>
                </td>
            </tr>
        `)
        getRole(user, "rolesID");
    }
}

//модальное окно для редактирования
async function modalEdit(id) {
    let res = await fetch(`api/users/${id}`);
    let user = await res.json();
    new bootstrap.Modal(document.querySelector('#edit')).show();

    formEdit.id.value = user.id;
    formEdit.firstName.value = user.firstName;
    formEdit.lastName.value = user.lastName;
    formEdit.email.value = user.email;
    formEdit.username.value = user.username;
    formEdit.password.value = user.password;
    let role = document.getElementById('rolesEdit');
    let roleID = user.roles.map(role => Number(role.id));
    for (let option of role.options) {
        if (roleID.includes(Number(option.value))) {
            option.setAttribute('selected', 'selected');
        } else {
            option.removeAttribute('selected');
        }
    }
}

//модальное окно для удаления
async function modalDelete(id) {
    let res = await fetch(`api/users/${id}`);
    let user = await res.json();
    new bootstrap.Modal(document.querySelector('#delete')).show();

    formDelete.id.value = user.id;
    formDelete.firstName.value = user.firstName;
    formDelete.lastName.value = user.lastName;
    formDelete.email.value = user.email;
    formDelete.username.value = user.username;
    let role = document.getElementById('rolesDelete');
    let roleID = user.roles.map(role => Number(role.id));
    for (let option of role.options) {
        if (roleID.includes(Number(option.value))) {
            option.setAttribute('selected', 'selected');
        } else {
            option.removeAttribute('selected');
        }
    }
}

//добавление данных
let formCreate = document.forms['create'];
formCreate.addEventListener('submit', async e => {
    e.preventDefault();
    let roles = [];
    for (let i = 0; i < formCreate.roles.options.length; i++) {
        if (formCreate.roles.options[i].selected) roles.push({
            id: formCreate.roles.options[i].value,
            name: "ROLE_" + formCreate.roles.options[i].text
        });
    }
    await fetch('api/users', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            firstName: formCreate.firstName.value,
            lastName: formCreate.lastName.value,
            email: formCreate.email.value,
            username: formCreate.username.value,
            password: formCreate.password.value,
            roles: roles
        })
    }).then(() => {
        formCreate.reset();
        $('#nav-table-tab').click();
        getAllUsers();
    });
});

//редактирование данных
let formEdit = document.forms['formEdit'];
document.forms['formEdit'].addEventListener('submit', async e => {
    e.preventDefault();
    let roles = [];
    for (let i = 0; i < formEdit.roles.options.length; i++) {
        if (formEdit.roles.options[i].selected) roles.push({
            id: formEdit.roles.options[i].value,
            name: "ROLE_" + formEdit.roles.options[i].text
        });
    }
    let res = await fetch('api/users', {
        method: 'PATCH',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            id: formEdit.id.value,
            firstName: formEdit.firstName.value,
            lastName: formEdit.lastName.value,
            email: formEdit.email.value,
            username: formEdit.username.value,
            password: formEdit.password.value,
            roles: roles
        })
    }).then(() => {
        formEdit.reset();
        $('#closeEdit').click();
        getAllUsers();
    })
});

//удаление данных
let formDelete = document.forms['formDelete'];
formDelete.addEventListener('submit', async e => {
    e.preventDefault();
    await fetch(`api/users/${formDelete.id.value}`, {
        method: 'DELETE',
        headers: {
            'Content-Type': 'application/json'
        }
    }).then(() => {
        formDelete.reset();
        $('#closeDelete').click();
        getAllUsers();
    });
});