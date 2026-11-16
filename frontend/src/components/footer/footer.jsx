import CopyRight from './copyright';
import './footer.css'

function Footer() {
    

    return (
        <>
            <footer>
                <div>
                    <h1>News letter</h1>
                    <p>Stay tuned with us!</p>
                    <input type='text' placeholder='Enter your email'/>
                    <button>Submit</button>
                </div>

                <div>
                    <h1>Shop With CloudCart</h1>
                    <p>CloudCart brings products, categories, checkout, and order tracking together in one convenient shopping experience.</p>
                    <button>Learn more</button>
                </div>


                <div>
                    <h1>Contact Us</h1>
                    <div>
                        <p><span><i className="fa fa-map-marker"></i></span>CloudCart Support Center</p>
                        <p><span><i className="fa fa-phone"></i></span>Support available through the CloudCart portal</p>
                        <p><span><i className="fa fa-envelope"></i></span>support@cloudcart.local</p>
                    </div>
                    <p>
                        <span><i className="fa fa-facebook"></i></span>
                        <span><i className="fa fa-twitter"></i></span>
                        <span><i className="fa fa-instagram"></i></span>
                        <span><i className="fa fa-youtube"></i></span>
                    </p>
                </div>
            </footer>
            <CopyRight />
        </>
    )
}

export default Footer;
