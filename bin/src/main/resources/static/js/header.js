const toggleButton = document.getElementsByClassName('toggle-button')[0]
const navbarLinks = document.getElementsByClassName('navbar-links')[0]

toggleButton.addEventListener('click', () => {
  navbarLinks.classList.toggle('active')
})

// ne funkcionira za potemnuvanje na menu pri izbor na edna strana i ostanuva so taa boja
 type="text/javascript">
      $("a.link").click(function(){
      $("a.link").css("background-color", "");
      $(this).css("background-color", "white");
});
