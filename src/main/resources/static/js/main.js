// scroll animation
const items=document.querySelectorAll('.fade-up');
window.addEventListener('scroll',()=>{
    items.forEach(el=>{
        if(el.getBoundingClientRect().top < window.innerHeight-100){
            el.classList.add('show');
        }
    });
});

// count animation
const counters=document.querySelectorAll('.count');
counters.forEach(counter=>{
    let start=0;
    const end=counter.dataset.target;
    const speed=30;
    const run=()=>{
        start+=Math.ceil(end/speed);
        if(start<end){
            counter.innerText=start;
            requestAnimationFrame(run);
        }else counter.innerText=end;
    };
    run();
});

//..........................
function showRegister(){
    document.getElementById("loginForm").classList.add("d-none");
    document.getElementById("registerForm").classList.remove("d-none");
}

function showLogin(){
    document.getElementById("registerForm").classList.add("d-none");
    document.getElementById("loginForm").classList.remove("d-none");
}
//......................
function confirmDonate(){
    document.getElementById("donateModal").classList.add("show");
    document.getElementById("donateModal").style.display="block";
}

function closeModal(){
    document.getElementById("donateModal").classList.remove("show");
    document.getElementById("donateModal").style.display="none";
}

function donated(){
    closeModal();
    alert("✅ Donation recorded successfully. Thank you for saving lives ❤️");
}



function toggleStatus(){
    const badge=document.getElementById("statusBadge");
    const check=document.getElementById("statusToggle");

    if(check.checked){
        badge.innerText="Eligible";
        badge.className="status-badge status-eligible";
    }else{
        badge.innerText="Not Eligible";
        badge.className="status-badge status-not";
    }
}



function approveAction(msg){
    alert("✅ " + msg + " approved successfully");
}

function rejectAction(msg){
    alert("❌ " + msg + " rejected");
}

function blockUser(){
    alert("🚫 Donor has been blocked");
}

    function loadStock(){

    const group = document.getElementById("bloodGroup").value;

    fetch('/stock/' + group)
    .then(res => res.text())
    .then(data => {

    const stock = parseInt(data);
    const text = document.getElementById("stockText");

    text.innerText = "Stock: " + stock;

    text.className = "stock-indicator";

    if(stock > 20){
    text.classList.add("stock-ok");
}else if(stock > 5){
    text.classList.add("stock-low");
}else{
    text.classList.add("stock-critical");
}

});
}
const params = new URLSearchParams(window.location.search);

if(params.get("donated") === "true"){

    const toastEl = document.getElementById('donateToast');
    const toast = new bootstrap.Toast(toastEl);
    toast.show();

    // Remove donated=true from URL (so refresh won't show again)
    window.history.replaceState({}, document.title, window.location.pathname);
}
document.addEventListener("DOMContentLoaded", () => {

    setTimeout(() => {

        document.getElementById("loadingRow")?.classList.add("d-none");

        let donorCount = document.getElementById("donorCount").value;

        if (donorCount == 0) {
            document.getElementById("emptyBox")?.classList.remove("d-none");
        } else {
            document.getElementById("donorRow")?.classList.remove("d-none");
        }

    }, 1200);

});
function goBack(){
    window.history.back();
}
