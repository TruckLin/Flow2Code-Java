 i m p o r t   j a v a . u t i l . S c a n n e r ; 
 p u b l i c   c l a s s   F l o w C o d e { 
         p u b l i c   s t a t i c   v o i d   m a i n ( S t r i n g [ ]   a r g s ) { 
 
                 i n t   i ; 
                 S c a n n e r   s c   =   n e w   S c a n n e r ( S y s t e m . i n ) ; 
                 i n t   j   =   s c . n e x t I n t ( ) ; 
                 S y s t e m . o u t . p r i n t l n ( " T h e   n u m b e r   e n t e r e d   i s   "   +   j ) ; 
                 s c . c l o s e ( ) ; 
 
                 f o r (   i   =   1 ;   i   < =   5 0 ;   i   =   i   +   3   )   { 
 
                         S y s t e m . o u t . p r i n t l n (   i   ) ; 
 
                 } 
         } 
 }