function getRole(user, id) {
    let roles = document.getElementById(id + user.id)
    for (let i = 0; i < user.roles.length; i++) {
        if (i > 0) {
            roles.append(" ");
        }
        roles.append(user.roles[i]['name'].replace("ROLE_", ""));
    }
}