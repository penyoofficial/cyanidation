function renderTest(time, cuts) {
    let today = document.querySelector("#today")
    today.innerHTML = time + "<br>"
    cuts.forEach(c => {
        today.innerHTML +=
            c.time[0] + "-" + c.time[1] + " " + c.name + "：" + c.place + "<br>"
    })
}

document.querySelector('input[type="file"]').onchange = async function (e) {
    const term = await Term.getInstance("HFUU", e.target.files[0])
    const cuts = term.getTodayCuts(new Date("2023-09-05"))
    renderTest(new Date("2023-09-05"), cuts)
    setInterval(() => {
        renderTest(new Date("2023-09-05"), cuts)
    }, 1800000)
}