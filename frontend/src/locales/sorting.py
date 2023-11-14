import json


# Fichier EN
with open('en.json', 'r', encoding='utf-8') as file:
    data = json.load(file)
sorted_keys = sorted(data.keys())
sorted_data = {key: data[key] for key in sorted_keys}
with open('en.json', 'w', encoding='utf-8') as sorted_file:
    json.dump(sorted_data, sorted_file, ensure_ascii=False, indent=2)


#Fichier FR
with open('fr.json', 'r', encoding='utf-8') as file:
    data = json.load(file)
sorted_keys = sorted(data.keys())
sorted_data = {key: data[key] for key in sorted_keys}
with open('fr.json', 'w', encoding='utf-8') as sorted_file:
    json.dump(sorted_data, sorted_file, ensure_ascii=False, indent=2)

print("Les clés du fichier JSON ont été triées avec succès.")