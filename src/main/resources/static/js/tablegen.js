function tableCreate() {
    const body = document.body,
        tbl = document.createElement('table');
   tbl.classList.add('table');
    /**
     input format json

     **/
    const arr = [
        'name',
        'role',
        'mail',
    ];
    const jsonList = '{"name": "DrunkardKirA", "role": "ADMIN"}';
    const obj = JSON.parse(jsonList)

    for (let col = 0; col < 2; col++) {
        const tr = tbl.insertRow();
        for (const row of arr) {
            const td = tr.insertCell();
            if (col < 1) {
                const head = row;
                td.appendChild(document.createTextNode(head));
            } else {
                if (obj[row]) {
                    td.appendChild(document.createTextNode(obj[row]));
                } else {
                    td.appendChild(document.createTextNode("NO DATA BEACH!!!"));
                }
            }
        }
    }
    body.appendChild(tbl);
}

/*tableCreate();*/
