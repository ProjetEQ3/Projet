import json

# Charger le fichier JSON
with open('fr.json', 'r', encoding='utf-8') as file:
    data = json.load(file)

# Trier les clés en ordre alphabétique
sorted_keys = sorted(data.keys())

# Créer un nouveau dictionnaire trié
sorted_data = {key: data[key] for key in sorted_keys}

# Sauvegarder le fichier JSON trié
with open('fr.json', 'w', encoding='utf-8', indent=2) as sorted_file:
    json.dump(sorted_data, sorted_file, ensure_ascii=False, indent=2)

print("Les clés du fichier JSON ont été triées avec succès.")