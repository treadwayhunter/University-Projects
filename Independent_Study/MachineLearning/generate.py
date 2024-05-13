import pandas as pd
import random
import math

def func(l):
    r = random.random()
    return (-l)*math.log(r)
    

movie_list = []
audience_score_list = []
critic_score_list = []
total_views_list = []

for i in range(100000):
    movie_list.append(f'Movie {i}')

    a_score = random.random()
    c_score = random.random()

    audience_score_list.append(a_score)
    critic_score_list.append(c_score)

    score_diff = abs(a_score - c_score)

    multiplier = a_score + c_score
    # let's say avg = 100,000
    if score_diff > 0.30:
        if a_score > c_score:
            multiplier = 3
        if c_score > a_score:
            multiplier = 0.20
    
    views = round(func(1000000)*multiplier)
    total_views_list.append(views)

# Generate csv file

data = {
    'movie_title': movie_list,
    'audience_score': audience_score_list,
    'critic_score': critic_score_list,
    'total_views': total_views_list
}

df = pd.DataFrame(data)

df.to_csv('sample.csv', index=False)