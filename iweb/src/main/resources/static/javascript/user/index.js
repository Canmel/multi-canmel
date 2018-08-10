$(function () {
    var clients = [

        {"username": "Otto Clay", "age": 25, "country": 1, "address": "Ap #897-1459 Quam Avenue", "married": false},
        {"username": "Connor Johnston", "age": 45, "country": 2, "address": "Ap #370-4647 Dis Av.", "married": true},
        {"username": "Lacey Hess", "age": 29, "country": 3, "address": "Ap #365-8835 Integer St.", "married": false},
        {"username": "Timothy Henson", "age": 56, "country": 1, "address": "911-5143 Luctus Ave", "married": true},
        {
            "username": "Ramona Benton",
            "age": 32,
            "country": 3,
            "address": "Ap #614-689 Vehicula Street",
            "married": false
        },
        {"username": "Otto Clay", "age": 25, "country": 1, "address": "Ap #897-1459 Quam Avenue", "married": false},
        {"username": "Connor Johnston", "age": 45, "country": 2, "address": "Ap #370-4647 Dis Av.", "married": true},
        {"username": "Lacey Hess", "age": 29, "country": 3, "address": "Ap #365-8835 Integer St.", "married": false},
        {"username": "Timothy Henson", "age": 56, "country": 1, "address": "911-5143 Luctus Ave", "married": true},
        {
            "username": "Ramona Benton",
            "age": 32,
            "country": 3,
            "address": "Ap #614-689 Vehicula Street",
            "married": false
        },
        {"username": "Otto Clay", "age": 25, "country": 1, "address": "Ap #897-1459 Quam Avenue", "married": false},
        {"username": "Connor Johnston", "age": 45, "country": 2, "address": "Ap #370-4647 Dis Av.", "married": true},
        {"username": "Lacey Hess", "age": 29, "country": 3, "address": "Ap #365-8835 Integer St.", "married": false},
        {"username": "Timothy Henson", "age": 56, "country": 1, "address": "911-5143 Luctus Ave", "married": true},
        {
            "username": "Ramona Benton",
            "age": 32,
            "country": 3,
            "address": "Ap #614-689 Vehicula Street",
            "married": false
        },
        {"username": "Otto Clay", "age": 25, "country": 1, "address": "Ap #897-1459 Quam Avenue", "married": false},
        {"username": "Connor Johnston", "age": 45, "country": 2, "address": "Ap #370-4647 Dis Av.", "married": true},
        {"username": "Lacey Hess", "age": 29, "country": 3, "address": "Ap #365-8835 Integer St.", "married": false},
        {"username": "Timothy Henson", "age": 56, "country": 1, "address": "911-5143 Luctus Ave", "married": true},
        {
            "username": "Ramona Benton",
            "age": 32,
            "country": 3,
            "address": "Ap #614-689 Vehicula Street",
            "married": false
        }
    ];

    var countries = [
        {Name: "", Id: 0},
        {Name: "United States", Id: 1},
        {Name: "Canada", Id: 2},
        {Name: "United Kingdom", Id: 3}
    ];

    jsGrid.locale("zh-cn");

    $("#jsGrid").jsGrid({
        width: "100%",
        height: "70%",
        inserting: true,
        editing: false,
        delting: true,
        sorting: true,
        paging: true,
        autoload: true,
        pageSize: 10,
        pageButtonCount: 5,
        data: clients,
        fields: [
            {name: "username", title: '姓名', type: "text", width: 150, validate: "required"},
            {name: "age", title: '年龄', index: 'age', type: "number", width: 50},
            {name: "address", title: '住址', type: "text", width: 200},
            {name: "country", title: '国籍', type: "select", items: countries, valueField: "Id", textField: "Name"},
            {name: "married", title: '婚配', type: "checkbox", sorting: false},
            {type: "control", title: '操作'}
        ]
    });
});