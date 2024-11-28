package gal.classexercises.producerconsumer.pizza;

public class Pizza {
    private int id;
    private double price;
    private String name;

    private String[] names = {
            "David's Special", "Julio's Style", "Cheese Please", "Pepperoni Party", "Veggie Delight",
            "Mamma Mia", "Margherita Magic", "BBQ Bonanza", "Hawaiian Hangover", "Spicy Sausage Surprise",
            "Mushroom Madness", "Four Cheese Fantasy", "Pineapple Peril", "Garlic Overload", "Meat Lover's Dream",
            "The Sizzler", "The Big Bite", "Crusty Crusader", "Saucy Supreme", "Cheesy Charm",
            "The Salami Slam", "The Flaming Frank", "Italian Stallion", "Mighty Mushroom", "The Hot One",
            "Crispy Critter", "Pizzageddon", "The Epic Crust", "Deep Dish Delight", "Tomato Tango",
            "BBQ Beast", "Cheese Avalanche", "The Royal Slice", "Pepperoni Paradise", "Sausage Seduction",
            "The Volcano", "The Overachiever", "The Grand Margherita", "Spicy Inferno", "The Breakfast Special",
            "The All-Star", "Big Bang Bite", "Super Sausage", "The Heavy Hitter", "The Big Kahuna",
            "The Garlic Godfather", "The Ultimate Italiano", "Cheese Crust Champion", "The Veggie Vortex",
            "The Meat Mountain", "The Spicy Slice", "Pizzapalooza", "The Inferno", "The Mighty Pepperoni",
            "Zesty Zucchini", "Bacon Bliss", "Pizzaroni Roll", "The Crusty Crusader", "Taco-tastic",
            "Pasta Pizza Fusion", "The Saucy Slice", "Bacon Bonanza", "Lava Lamp", "The Mushroom Mule",
            "The Hot & Heavy", "Basil Banger", "The Hawaiian Hurricane", "Zingy Ziti", "Meatball Masterpiece",
            "Chili Cheese Championship", "The Midnight Snack", "Viva la Veggie", "Classic Crust", "The Cheesy Chomp",
            "Smokey Sunrise", "The Flaming Fungi", "Pesto Perfection", "Double Cheese Delight",
            "The Garlic Gladiator", "Fiery Fiesta", "The Sweet & Savory", "The Starlight Slice", "The One That Got Away",
            "Thunderbolt Toss", "The Artisan Affair", "The Funky Crust", "Supernova Slice", "Baconator Supreme",
            "The Saucy Supreme", "Slice of Life", "The Outrageous Original", "The Wild One", "Pizza Party Panic",
            "The Ultimate Sizzle", "The Pie in the Sky", "Pizzarama", "The Lava Lover", "Pepperoni Paradise",
            "The Big Bang", "Mozzarella Magic", "Margherita Madness", "Garlic Galore", "The Deep Dish Disaster"
    };

    public Pizza(int id) {
        this.id = id;
        this.price = 10 + (Math.random() * 40);
        this.name = names[(int) (Math.random() * names.length)];
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getPrice() {
        String formattedPrice = String.format("%.2f", price).replace(',', '.');
        return Double.parseDouble(formattedPrice);
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Pizza number " + id +
                " costs " + price +
                '€';
    }
}


