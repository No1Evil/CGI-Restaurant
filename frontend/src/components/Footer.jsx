function Footer() {

    const currentYear = new Date().getFullYear();

    return (
        <footer className="bg-sky-900 p-8 text-center flex justify-center align-bottom">
            <p className="text-sm text-white">
                &copy; {currentYear} Name Restaurant. All rights may not be protected.
            </p>
        </footer>
    )
}

export default Footer;