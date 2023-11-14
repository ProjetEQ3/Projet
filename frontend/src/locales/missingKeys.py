import json

# Charger les fichiers JSON
with open('en.json', 'r', encoding='utf-8') as file1:
    data1 = json.load(file1)

with open('fr.json', 'r', encoding='utf-8') as file2:
    data2 = json.load(file2)

# Extraire les ensembles de clés des deux fichiers
keys1 = set(data1.keys())
keys2 = set(data2.keys())

# Trouver les clés manquantes dans le fichier 2 par rapport au fichier 1
missing_keys_in_file2 = keys1 - keys2
missing_keys_in_file1 = keys2 - keys1

# Afficher les clés manquantes
if len(missing_keys_in_file2) == 0:
    print("Aucune clé manquante dans le fichier fr.")
else:
    print("Clés manquantes dans le fichier fr:")
    for key in missing_keys_in_file2:
        print(key)
if len(missing_keys_in_file1) == 0:
    print("Aucune clé manquante dans le fichier en.")
else:
    print("Clés manquantes dans le fichier en:")
    for key in missing_keys_in_file1:
        print(key)
