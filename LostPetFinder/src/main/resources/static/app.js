// =====================================================
// LOSTPETFINDER - FRONTEND JAVASCRIPT
// =====================================================


// ================= DASHBOARD =================

async function loadDashboard() {

    try {

        const response =
            await fetch('/api/dashboard/summary');

        if (!response.ok) {
            throw new Error('Dashboard request failed');
        }

        const data = await response.json();

        document.getElementById('totalLost').textContent =
            data.totalLost ?? 0;

        document.getElementById('activeLost').textContent =
            data.activeLost ?? 0;

        document.getElementById('totalFound').textContent =
            data.totalFound ?? 0;

        document.getElementById('totalUsers').textContent =
            data.totalUsers ?? 0;

    } catch (error) {

        console.error('Dashboard error:', error);

    }
}


// ================= BADGE =================

function badge(value, type) {

    const cls =
        type || String(value).toLowerCase();

    return `
        <span class="badge ${cls}">
            ${value}
        </span>
    `;
}


// ================= SEARCH REPORTS =================

async function searchReports(event) {

    event.preventDefault();

    const locality =
        document.getElementById('locality').value.trim();

    const message =
        document.getElementById('message');

    const results =
        document.getElementById('results');

    message.textContent = '';

    if (!locality) {
        return;
    }

    results.innerHTML = `
        <tr>
            <td colspan="6" class="empty">
                Searching reports...
            </td>
        </tr>
    `;

    try {

        const response =
            await fetch(
                '/api/reports/search?locality=' +
                encodeURIComponent(locality)
            );

        if (!response.ok) {
            throw new Error('Search failed');
        }

        const rows =
            await response.json();

        if (!rows.length) {

            results.innerHTML = `
                <tr>
                    <td colspan="6" class="empty">
                        No reports found for this locality.
                    </td>
                </tr>
            `;

            return;
        }

        results.innerHTML = rows.map(report => {

            return `
                <tr>

                    <td>
                        ${badge(
                            report.type,
                            String(report.type).toLowerCase()
                        )}
                    </td>

                    <td>
                        ${report.species || '—'}
                    </td>

                    <td>
                        ${report.breed || '—'}
                    </td>

                    <td>
                        ${report.color || '—'}
                    </td>

                    <td>
                        ${report.location || '—'}
                    </td>

                    <td>
                        ${badge(
                            report.status,
                            String(report.status).toLowerCase()
                        )}
                    </td>

                </tr>
            `;

        }).join('');

    } catch (error) {

        console.error(error);

        message.textContent =
            'Unable to search right now. Make sure the Spring Boot server is running.';

        results.innerHTML = `
            <tr>
                <td colspan="6" class="empty">
                    Search could not be completed.
                </td>
            </tr>
        `;
    }
}


// ================= REGISTER USER =================

async function registerUser(event) {

    event.preventDefault();

    const message =
        document.getElementById('registerMessage');

    message.textContent = 'Creating your account...';

    const name =
        document.getElementById('registerName').value.trim();

    const email =
        document.getElementById('registerEmail').value.trim();

    const phone =
        document.getElementById('registerPhone').value.trim();


    const userData = {

        name: name,

        email: email,

        phone: phone

    };


    try {

        const response =
            await fetch('/api/users', {

                method: 'POST',

                headers: {
                    'Content-Type': 'application/json'
                },

                body: JSON.stringify(userData)

            });


        if (!response.ok) {

            let errorMessage =
                'Registration failed.';

            try {

                const error =
                    await response.json();

                if (error.message) {
                    errorMessage = error.message;
                }

            } catch (_) {}

            throw new Error(errorMessage);
        }


        const user =
            await response.json();


        message.innerHTML = `
            <span style="color:#55d99a;">
                ✓ Registration successful!
                Your User ID is <strong>${user.id}</strong>.
            </span>
        `;


        document
            .getElementById('registerForm')
            .reset();


        loadDashboard();


        document
            .getElementById('lostUserId')
            .value = user.id;

        document
            .getElementById('foundUserId')
            .value = user.id;


    } catch (error) {

        console.error('Registration error:', error);

        message.innerHTML = `
            <span style="color:#ff947c;">
                ✕ ${error.message}
            </span>
        `;
    }
}


// ================= REPORT LOST PET =================

async function reportLostPet(event) {

    event.preventDefault();

    const message =
        document.getElementById('lostMessage');

    message.textContent =
        'Submitting lost pet report...';


    const data = {

        species:
            document.getElementById('lostSpecies')
                .value.trim(),

        breed:
            document.getElementById('lostBreed')
                .value.trim(),

        color:
            document.getElementById('lostColor')
                .value.trim(),

        lastSeenLocation:
            document.getElementById('lostLocation')
                .value.trim(),

        userId:
            Number(
                document.getElementById('lostUserId')
                    .value
            )
    };


    try {

        const response =
            await fetch('/api/lost-pets', {

                method: 'POST',

                headers: {
                    'Content-Type': 'application/json'
                },

                body: JSON.stringify(data)

            });


        if (!response.ok) {

            let errorMessage =
                'Unable to submit lost pet report.';

            try {

                const error =
                    await response.json();

                if (error.message) {
                    errorMessage = error.message;
                }

            } catch (_) {}

            throw new Error(errorMessage);
        }


        const report =
            await response.json();


        message.innerHTML = `
            <span style="color:#55d99a;">
                ✓ Lost pet report submitted successfully!
                Report ID: <strong>${report.id}</strong>
            </span>
        `;


        document
            .getElementById('lostPetForm')
            .reset();


        loadDashboard();

    } catch (error) {

        console.error('Lost pet error:', error);

        message.innerHTML = `
            <span style="color:#ff947c;">
                ✕ ${error.message}
            </span>
        `;
    }
}


// ================= REPORT FOUND ANIMAL =================

async function reportFoundAnimal(event) {

    event.preventDefault();

    const message =
        document.getElementById('foundMessage');

    message.textContent =
        'Submitting found animal report...';


    const data = {

        species:
            document.getElementById('foundSpecies')
                .value.trim(),

        breed:
            document.getElementById('foundBreed')
                .value.trim(),

        color:
            document.getElementById('foundColor')
                .value.trim(),

        foundLocation:
            document.getElementById('foundLocation')
                .value.trim(),

        userId:
            Number(
                document.getElementById('foundUserId')
                    .value
            )
    };


    try {

        const response =
            await fetch('/api/found-animals', {

                method: 'POST',

                headers: {
                    'Content-Type': 'application/json'
                },

                body: JSON.stringify(data)

            });


        if (!response.ok) {

            let errorMessage =
                'Unable to submit found animal report.';

            try {

                const error =
                    await response.json();

                if (error.message) {
                    errorMessage = error.message;
                }

            } catch (_) {}

            throw new Error(errorMessage);
        }


        const report =
            await response.json();


        message.innerHTML = `
            <span style="color:#55d99a;">
                ✓ Found animal report submitted successfully!
                Report ID: <strong>${report.id}</strong>
            </span>
        `;


        document
            .getElementById('foundAnimalForm')
            .reset();


        loadDashboard();

    } catch (error) {

        console.error('Found animal error:', error);

        message.innerHTML = `
            <span style="color:#ff947c;">
                ✕ ${error.message}
            </span>
        `;
    }
}


// ================= EVENT LISTENERS =================

document
    .getElementById('searchForm')
    .addEventListener(
        'submit',
        searchReports
    );


document
    .getElementById('registerForm')
    .addEventListener(
        'submit',
        registerUser
    );


document
    .getElementById('lostPetForm')
    .addEventListener(
        'submit',
        reportLostPet
    );


document
    .getElementById('foundAnimalForm')
    .addEventListener(
        'submit',
        reportFoundAnimal
    );


// ================= START DASHBOARD =================

loadDashboard();