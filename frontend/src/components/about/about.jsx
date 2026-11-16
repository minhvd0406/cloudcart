import './about.css'

function About() {
    return (
        <section className="about">
            <div className="about-image"><h3>Are you ready to live well?</h3></div>
            <div className="about-content">
                <h1>Who we are?</h1>
                <p>Welcome to CloudCart, your destination for premium health and wellness products designed to nourish your body, mind, and soul. We believe that true wellness is a journey, and we're here to support you every step of the way.</p>
                <p>At CloudCart, our mission is to empower healthier, happier lives with carefully curated products that prioritize quality, efficacy, and sustainability.</p>
                <p>From nutritional supplements and fitness equipment to skincare essentials and relaxation aids, CloudCart offers a diverse range of products to support your wellness journey.</p>
                <div className="icon-container">
                    <div className="icon">
                        <i className="fa-solid fa-truck-fast"></i>
                        <span>Island wide Delivery</span>
                    </div>

                    <div className="icon">
                        <i className="fa-regular fa-credit-card"></i>
                        <span>Secure payments</span>
                    </div>

                    <div className="icon">
                        <i className="fa-solid fa-hand-holding-dollar"></i>
                        <span>Affordable price</span>
                    </div>

                    <div className="icon">
                        <i className="fa fa-leaf"></i>
                        <span>Our green journey</span>
                    </div>
                </div>
            </div>

        </section>
    )
}

export default About
