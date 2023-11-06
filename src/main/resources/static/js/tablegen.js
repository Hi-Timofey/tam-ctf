function tableCreate() {
    const body = document.body,
        tbl = document.createElement('table');
    tbl.classList.add('table');
    /**
     input format json wia string or array
     **/
    const arr = [
        'name',
        'role',
        'mail',
        'status',
        'test',
        'hello world!',
    ];
    const jsonList = [
        '{"name": "DrunkardKirA", "role": "ADMIN"}',
        '{"name": "katok", "role": "TEAMLEAD"}',
        '{"name": "Feeronus", "role": "ADMIN"}'
    ];
    const trhead = tbl.insertRow();
    for (const row of arr) {
        const tdhead = trhead.insertCell();
        const tableHead = row;
        tdhead.appendChild(document.createTextNode(tableHead));
    }
    for (const jsonObj in jsonList) {
        const obj = JSON.parse(jsonList[jsonObj])

        console.log(obj)
        const tr = tbl.insertRow();
        for (const row of arr) {
            const td = tr.insertCell();
            if (obj[row]) {
                td.appendChild(document.createTextNode(obj[row]));
            } else {
                td.appendChild(document.createTextNode("NO DATA BEACH!!!"));
            }
        }
    }
    /*}*/
    body.appendChild(tbl);
}
