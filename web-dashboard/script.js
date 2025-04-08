const fetchData = async () => {
    try {
        const response = await fetch('https://incldigitrans-5881.restdb.io/rest/cases-disease', {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
                'x-apikey': '67f3e466636df6b1f15d955a',
                'cache-control': 'no-cache'
            }
        });

        if (!response.ok) {
            throw new Error(`HTTP error! Status: ${response.status}`);
        }

        const data = await response.json();
        processData(data);
    } catch (error) {
        console.error('Error fetching data:', error);
        document.getElementById('app').innerHTML = `<p class="text-red-500 text-center">Failed to load data.</p>`;
    }
};

fetchData();

const processData = (data) => {
    if (!Array.isArray(data) || data.length === 0) {
        console.error('No data available or invalid data format:', data);
        document.getElementById('app').innerHTML = `<p class="text-yellow-500 text-center">No data available to display.</p>`;
        return;
    }

    const genderCounts = {};
    const diseaseCounts = {};

    data.forEach(record => {
        genderCounts[record['Client Gender']] = (genderCounts[record['Client Gender']] || 0) + 1;

        diseaseCounts[record['Disease Classification']] = (diseaseCounts[record['Disease Classification']] || 0) + 1;
    });

    renderCharts(genderCounts, diseaseCounts);
};

const renderCharts = (genderCounts, diseaseCounts) => {
    const genderCtx = document.getElementById('genderChart').getContext('2d');
    new Chart(genderCtx, {
        type: 'bar',
        data: {
            labels: Object.keys(genderCounts),
            datasets: [{
                label: 'Cases by Gender',
                data: Object.values(genderCounts),
                backgroundColor: ['#3b82f6', '#f97316'],
                borderColor: ['#3b82f6', '#f97316'],
                borderWidth: 1
            }]
        },
        options: {
            responsive: true,
            scales: {
                x: {
                    ticks: {
                        color: 'white'
                    }
                },
                y: {
                    ticks: {
                        color: 'white',
                        beginAtZero: true
                    }
                }
            },
            plugins: {
                title: {
                    display: true,
                    text: 'Cases Disaggregated by Gender',
                    color: 'white'
                },
                legend: {
                    labels: {
                        color: 'white'
                    }
                }
            }
        }
    });

    const diseaseCtx = document.getElementById('diseaseChart').getContext('2d');
    new Chart(diseaseCtx, {
        type: 'pie',
        data: {
            labels: Object.keys(diseaseCounts),
            datasets: [{
                label: 'Cases by Disease',
                data: Object.values(diseaseCounts),
                backgroundColor: [
                    '#ef4444',
                    '#10b981',
                    '#6366f1',
                    '#f59e0b',
                    '#3b82f6',
                    '#f97316',
                    '#8b5cf6',
                    '#06b6d4',
                    '#eab308',
                    '#14b8a6',
                    '#000000'
                ],
                hoverOffset: 4
            }]
        },
        options: {
            responsive: true,
            plugins: {
                title: {
                    display: true,
                    text: 'Cases Disaggregated by Disease Classification',
                    color: 'white'
                },
                legend: {
                    labels: {
                        color: 'white'
                    }
                }
            }
        }
    });
};