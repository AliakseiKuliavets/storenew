package com.aliakseikul.storenew.service.entit;

import com.aliakseikul.storenew.entity.Product;
import com.aliakseikul.storenew.entity.User;
import com.aliakseikul.storenew.entity.enums.ProductBrand;
import com.aliakseikul.storenew.entity.enums.ProductCategory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class TestListProduct {

    public static List<Product> PRODUCTS = new ArrayList<>();

    public static void clear() {
        PRODUCTS = Arrays.asList(
                new Product(
                        UUID.fromString("c2bfcb8e-af96-43c8-88e2-56f3a78e2436"),
                        "iPhone",
                        250.0,
                        "Simple Text Description",
                        ProductCategory.ELECTRONICS,
                        ProductBrand.SAMSUNG,
                        new User(),
                        new User()
                ),
                new Product(
                        UUID.fromString("b00dd15c-084d-4c3d-aae1-f51d5b631e88"),
                        "Smartphone",
                        299.99,
                        "Feature-packed smartphone for everyday use",
                        ProductCategory.ELECTRONICS,
                        ProductBrand.SAMSUNG,
                        new User(),
                        new User()
                ), new Product(
                        UUID.fromString("17024214-bea5-4293-b576-6d13e2035636"),
                        "Athletic Shoes",
                        79.99,
                        "Comfortable sports shoes for running and training",
                        ProductCategory.SPORTS,
                        ProductBrand.ASUS,
                        new User(),
                        new User()
                ), new Product(
                        UUID.fromString("959637da-2efc-41b8-93a3-86c3acddf3a8"),
                        "Smartphone",
                        199.99,
                        "Automate your home with this smart home starter kit",
                        ProductCategory.ELECTRONICS,
                        ProductBrand.PHILIPS,
                        new User(),
                        new User()
                ), new Product(
                        UUID.fromString("f47ac10b-58cc-4372-a567-0e02b2c3d479"),
                        "Smartphone ABC",
                        349.99,
                        "Affordable smartphone with essential features",
                        ProductCategory.ELECTRONICS,
                        ProductBrand.SAMSUNG,
                        new User(),
                        new User()
                ), new Product(
                        UUID.fromString("c7577f6c-b5e6-47f1-8c4c-a9c4a6f6d0b9"),
                        "Running Shoes",
                        59.99,
                        "Lightweight running shoes for fitness enthusiasts",
                        ProductCategory.SPORTS,
                        ProductBrand.NIKE,
                        new User(),
                        new User()
                ), new Product(
                        UUID.fromString("097da9b6-aaf2-4b0e-98df-422f74d71446"),
                        "Smart Home Security System",
                        199.99,
                        "Protect your home with this advanced security system",
                        ProductCategory.ELECTRONICS,
                        ProductBrand.PHILIPS,
                        new User(),
                        new User()
                ), new Product(
                        UUID.fromString("12e4a0e0-b078-4e1a-85c6-9d8574e28c3a"),
                        "Mystery Puzzle Game",
                        24.99,
                        "Challenge yourself with this intriguing mystery puzzle game",
                        ProductCategory.OTHER,
                        ProductBrand.HASBRO,
                        new User(),
                        new User()
                ), new Product(
                        UUID.fromString("671e602d-5e91-4ff1-a682-5a8f3997ac27"),
                        "Anti-Gravity Yoga Mat",
                        49.99,
                        "Experience a unique yoga session with this anti-gravity mat",
                        ProductCategory.SPORTS,
                        ProductBrand.NIKE,
                        new User(),
                        new User()
                ), new Product(
                        UUID.fromString("f6d671c9-6ab2-4a8e-92e0-06ea86a5fe2b"),
                        "Wireless Earbuds",
                        79.99,
                        "Enjoy high-quality audio with these wireless earbuds",
                        ProductCategory.ELECTRONICS,
                        ProductBrand.LG,
                        new User(),
                        new User()
                ), new Product(
                        UUID.fromString("a3845fb7-7a13-4e0b-841d-8ec0a2851777"),
                        "Board Game Night Bundle",
                        39.99,
                        "Gather friends for a night of board game fun with this bundle",
                        ProductCategory.OTHER,
                        ProductBrand.HASBRO,
                        new User(),
                        new User()
                ), new Product(
                        UUID.fromString("b7a93569-6718-4c66-8c3a-5c8aa16a7959"),
                        "Car Emergency Kit",
                        89.99,
                        "Be prepared for emergencies with this comprehensive kit",
                        ProductCategory.OTHER,
                        ProductBrand.BLACK_AND_DECKER,
                        new User(),
                        new User()
                ), new Product(
                        UUID.fromString("e736d8ae-1fe9-43f0-93f7-0c6097dca2e1"),
                        "Herbal Tea Sampler",
                        19.99,
                        "Explore a variety of herbal teas with this sampler",
                        ProductCategory.OTHER,
                        ProductBrand.HASBRO,
                        new User(),
                        new User()
                ), new Product(
                        UUID.fromString("1c5dd31b-2e94-4e61-b8e4-cac9000d9c56"),
                        "Bluetooth Noise-Canceling Headphones",
                        129.99,
                        "Immerse yourself in music with these noise-canceling headphones",
                        ProductCategory.ELECTRONICS,
                        ProductBrand.ASUS,
                        new User(),
                        new User()
                ), new Product(
                        UUID.fromString("5eb768b7-6a18-4a92-b0e3-b42b0da249d2"),
                        "Casual Backpack",
                        34.99,
                        "Everyday backpack for a casual and stylish look",
                        ProductCategory.OTHER,
                        ProductBrand.ADIDAS,
                        new User(),
                        new User()
                ), new Product(
                        UUID.fromString("a7cb1b8e-8f0c-45cf-982a-5c6a965f7582"),
                        "Indoor Plant Set",
                        29.99,
                        "Bring nature indoors with this set of low-maintenance plants",
                        ProductCategory.OTHER,
                        ProductBrand.ADIDAS,
                        new User(),
                        new User()
                ), new Product(
                        UUID.fromString("430d1b72-9928-40b4-8ee4-58c76b04c85b"),
                        "Classic Sci-Fi Movie Collection",
                        69.99,
                        "Build your movie collection with these classic sci-fi films",
                        ProductCategory.OTHER,
                        ProductBrand.DELL,
                        new User(),
                        new User()
                ), new Product(
                        UUID.fromString("8c5f2a2c-c8e3-40ef-9d7f-af50ac3b54f9"),
                        "Natural Skincare Set",
                        44.99,
                        "Pamper your skin with this natural skincare set",
                        ProductCategory.OTHER,
                        ProductBrand.NIKE,
                        new User(),
                        new User()
                ), new Product(
                        UUID.fromString("9e1208a2-14ea-4cf3-81d6-5ebc83d288bf"),
                        "Basketball and Hoop Combo",
                        149.99,
                        "Get active with this basketball and hoop combo set",
                        ProductCategory.SPORTS,
                        ProductBrand.ADIDAS,
                        new User(),
                        new User()
                ), new Product(
                        UUID.fromString("d02825e2-1845-40f4-b067-b249c7382b29"),
                        "Home Office Desk",
                        199.99,
                        "Create a productive workspace with this stylish desk",
                        ProductCategory.OTHER,
                        ProductBrand.DELL,
                        new User(),
                        new User()
                ), new Product(
                        UUID.fromString("b0b5d82f-3218-4aeb-87c5-39f156f1cfad"),
                        "Wireless Charging Pad",
                        29.99,
                        "Conveniently charge your devices with this wireless pad",
                        ProductCategory.ELECTRONICS,
                        ProductBrand.SAMSUNG,
                        new User(),
                        new User()
                ), new Product(
                        UUID.fromString("820e90bb-5cf1-4647-8f91-c2cf11e8c2b2"),
                        "Cycling Helmet",
                        39.99,
                        "Stay safe on your cycling adventures with this helmet",
                        ProductCategory.SPORTS,
                        ProductBrand.NIKE,
                        new User(),
                        new User()
                ), new Product(
                        UUID.fromString("d17c181a-1420-4d92-8764-9830986a0e9f"),
                        "Compact Coffee Maker",
                        49.99,
                        "Brew your favorite coffee with this space-saving coffee maker",
                        ProductCategory.ELECTRONICS,
                        ProductBrand.PHILIPS,
                        new User(),
                        new User()
                ), new Product(
                        UUID.fromString("db0aeb91-2389-4c3d-a099-0daec6789b1f"),
                        "Hiking Backpack",
                        79.99,
                        "Explore the outdoors with this durable hiking backpack",
                        ProductCategory.OTHER,
                        ProductBrand.SAMSUNG,
                        new User(),
                        new User()
                ), new Product(
                        UUID.fromString("52a042d3-c983-42a8-8f5d-3f6636505353"),
                        "Bluetooth Gaming Headset",
                        119.99,
                        "Enhance your gaming experience with this immersive headset",
                        ProductCategory.ELECTRONICS,
                        ProductBrand.DELL,
                        new User(),
                        new User()
                ), new Product(
                        UUID.fromString("b06ea0b1-85da-4f01-9c1d-7f34f2a4c3f0"),
                        "Dumbbell Set",
                        69.99,
                        "Build strength at home with this versatile dumbbell set",
                        ProductCategory.SPORTS,
                        ProductBrand.NIKE,
                        new User(),
                        new User()
                ), new Product(
                        UUID.fromString("8743e86d-0c4f-4f93-b0cb-02a4e77c1483"),
                        "Portable Power Bank",
                        29.99,
                        "Charge your devices on the go with this portable power bank",
                        ProductCategory.ELECTRONICS,
                        ProductBrand.APPLE,
                        new User(),
                        new User()
                ), new Product(
                        UUID.fromString("cbbf1ad3-888f-4b47-99a0-84e2419ec36e"),
                        "Yoga Mat with Alignment Lines",
                        54.99,
                        "Improve your yoga practice with this alignment-focused mat",
                        ProductCategory.SPORTS,
                        ProductBrand.LG,
                        new User(),
                        new User()
                ), new Product(
                        UUID.fromString("f05ba377-e8ab-4e94-8c12-62e69356a5a6"),
                        "Wireless Gaming Mouse",
                        49.99,
                        "Elevate your gaming setup with this responsive wireless mouse",
                        ProductCategory.ELECTRONICS,
                        ProductBrand.ASUS,
                        new User(),
                        new User()
                ), new Product(
                        UUID.fromString("9e8a152a-9f5b-4287-8f3b-ea5e68bfab24"),
                        "Cookware Set",
                        89.99,
                        "Upgrade your kitchen with this high-quality cookware set",
                        ProductCategory.OTHER,
                        ProductBrand.BLACK_AND_DECKER,
                        new User(),
                        new User()
                ), new Product(
                        UUID.fromString("f11ec7b1-4a19-4ab7-93a2-9b47a207249f"),
                        "Fitness Exercise Ball",
                        19.99,
                        "Strengthen your core with this versatile fitness ball",
                        ProductCategory.SPORTS,
                        ProductBrand.ADIDAS,
                        new User(),
                        new User()
                ), new Product(
                        UUID.fromString("20746c0e-9615-4d50-9e6a-1dfac54b254d"),
                        "Smart Thermostat",
                        129.99,
                        "Efficiently control your home's temperature with this smart thermostat",
                        ProductCategory.ELECTRONICS,
                        ProductBrand.DELL,
                        new User(),
                        new User()
                ), new Product(
                        UUID.fromString("8ee6f22f-02b7-4427-8724-5569a7cc362c"),
                        "Sustainable Bamboo Toothbrush Set",
                        12.99,
                        "Make an eco-friendly choice with this bamboo toothbrush set",
                        ProductCategory.OTHER,
                        ProductBrand.DELL,
                        new User(),
                        new User()
                ), new Product(
                        UUID.fromString("f9e7776c-53a4-41b0-80a2-c5a2892df3c3"),
                        "Smart LED Bulbs",
                        29.99,
                        "Control your lighting with these energy-efficient smart LED bulbs",
                        ProductCategory.ELECTRONICS,
                        ProductBrand.PHILIPS,
                        new User(),
                        new User()
                ), new Product(
                        UUID.fromString("765eff6f-1162-4a4f-a54a-06f2c7c1a8e7"),
                        "Camping Tent",
                        79.99,
                        "Enjoy the great outdoors with this spacious camping tent",
                        ProductCategory.OTHER,
                        ProductBrand.APPLE,
                        new User(),
                        new User()
                ), new Product(
                        UUID.fromString("45e7a108-4f94-4dcd-b149-9e87e53f72a2"),
                        "Electric Toothbrush",
                        49.99,
                        "Upgrade your oral care routine with this electric toothbrush",
                        ProductCategory.ELECTRONICS,
                        ProductBrand.ADIDAS,
                        new User(),
                        new User()
                ), new Product(
                        UUID.fromString("d6a8c35b-9b07-4a7a-8e3e-d8a9c04d1656"),
                        "Resistance Band Set",
                        24.99,
                        "Enhance your workout with this versatile resistance band set",
                        ProductCategory.SPORTS,
                        ProductBrand.LG,
                        new User(),
                        new User()
                ), new Product(
                        UUID.fromString("6752876b-9ef1-4213-bf99-85a810a3d2de"),
                        "Coffee Grinder",
                        34.99,
                        "Grind your favorite coffee beans for a fresh brew",
                        ProductCategory.ELECTRONICS,
                        ProductBrand.NIKE,
                        new User(),
                        new User()
                ));
    }
}
