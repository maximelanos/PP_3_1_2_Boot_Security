getInfoUser();

async function getInfoUser() {
    let info = await fetch("userApi/auth");
    let user = await info.json();

    document.getElementById('principal').insertAdjacentHTML("beforeend", `
        <span>${user.email}</span>
        <span> с ролями: </span>
        <span id="principalID${user.id}"></span>
    `)
    getRole(user, "principalID");

    document.getElementById('userInfo').insertAdjacentHTML('beforeend', `
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

